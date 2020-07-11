package edu.example.broders.englishwords;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import utils.DataBaseHelper;

public class Question extends AppCompatActivity {

    private TextView theme_questions;
    private TextView question;
    private EditText editTextreponse;
    private String theme;
    private int motRestant;
    private boolean firstStrike = true;
    protected static int scoreFirstStrike = 0 ;
    protected static int totalError = 0;
    protected static int found = 0;
    private static String choixLangue;
    Random rand = new Random(  );
    private boolean aide=false;
    private DataBaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.question_other_version );
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        myDb = new DataBaseHelper(this);
        editTextreponse = findViewById( R.id.reponse_questions );
        final Intent intent = getIntent();
        choixLangue = intent.getStringExtra( "choixLangue" ).toString();
        theme = intent.getStringExtra( "theme_questions" ).toString();
        //initLangue( choixLangue );
        theme_questions = findViewById( R.id.theme_questions );
        question = findViewById( R.id.question );
        theme = intent.getStringExtra( "theme_questions" ).toString();
        final int i = rand.nextInt( BoiteAQuestion.tailleDeLaBoite());
        motRestant =  intent.getIntExtra( "wordCount",10 );
        if(motRestant == 1){
            theme_questions.setText("Thème\n"+theme+"\n Il reste 1 mot ");
        }else{
            theme_questions.setText("Thème\n"+theme+"\n Il reste "+motRestant+" mots");
        }
        String motADeviner;
        final List<String> reponse = new ArrayList<>();
        if(choixLangue.equals( "Anglais → Français" )){
            motADeviner = BoiteAQuestion.recupMotEn();
            for (String s : BoiteAQuestion.recupMotFr().split(",")) {
                reponse.add(s);
            }

        }else{
            motADeviner = BoiteAQuestion.recupMotFr();
            reponse.add(BoiteAQuestion.recupMotEn());
        }
        if(BoiteAQuestion.tailleDeLaBoite() > 1){
            BoiteAQuestion.supprimerQuestion();
        }else if(BoiteAQuestion.tailleDeLaBoite() == 1){
            Cursor res = myDb.getAllWordsOfTheQUIZZ(theme);
            List<String> motsFr = new ArrayList<>();
            List<String> motsEn = new ArrayList<>();
            List<String> contextes = new ArrayList<>();
            if(res.getCount()==0){
                Toast.makeText(Question.this, "Pas de mot trouvé pour ce quizz !", Toast.LENGTH_LONG).show();
            }else {
                while (res.moveToNext()) {
                    motsFr.add(res.getString(1));
                    motsEn.add(res.getString(2));
                    contextes.add(res.getString(3));
                }
            }
            BoiteAQuestion.BoiteAQuestion(motsFr,motsEn,contextes);
        }
        question.setText(motADeviner);


        editTextreponse.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    boolean trouve = false;
                    if(editTextreponse.getText().toString().length() > 0){
                        if(stripAccents(reponse).contains(stripAccents(editTextreponse.getText().toString()) )){
                            trouve = true;
                        }

                        if(trouve){
                            if(!aide){
                                editTextreponse.setBackgroundColor( Color.GREEN);
                                String res = reponse.toString().replace(", ", ",").replaceAll("[\\[.\\]]", "");
                                myDb.upScore(res,choixLangue);
                            }
                            motRestant --;
                            if(firstStrike){
                                scoreFirstStrike ++;
                            }else{
                                found++;
                            }
                            if(motRestant == 0){
                                Intent intent3 = new Intent( Question.this,EndGame.class );
                                intent3.putExtra( "scoreFirstStrike",scoreFirstStrike );
                                intent3.putExtra( "found",found );
                                intent3.putExtra( "scoreError",totalError );
                                startActivity( intent3 );
                                finish();
                            }else {
                                Intent intent2 = new Intent( Question.this, Question.class );
                                intent2.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent2.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent2.putExtra( "theme_questions", theme );
                                intent2.putExtra( "choixLangue",choixLangue );
                                intent2.putExtra( "wordCount", motRestant );
                                startActivity( intent2 );
                                finish();
                            }
                        }else{
                            if(firstStrike){
                                firstStrike = false;
                            }
                            totalError ++;
                            if(aide=((totalError%3)==0)){
                                editTextreponse.setBackgroundColor( Color.YELLOW);
                                editTextreponse.setText(reponse.get(0));

                            }else{
                                editTextreponse.setBackgroundColor(Color.rgb( 255,136,128 ));
                            }

                        }
                    }

                    return true;
                }
                return false;
            }
        });


    }

    public void onBackPressed() {
        // Here you want to show the user a dialog box
        new AlertDialog.Builder(this)
                .setTitle("Partie en cours")
                .setMessage("Voulez vous arrêter le quizz ? ")
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // The user wants to leave - so dismiss the dialog and exit
                        finish();
                        dialog.dismiss();
                    }
                }).setNegativeButton("Non", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // The user is not sure, so you can exit or just stay
                dialog.dismiss();
            }
        }).show();

    }


    public static String stripAccents(String s)
    {
        s = Normalizer.normalize(s, Normalizer.Form.NFD);
        s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        s = s.replaceAll("\\s+$", "");
        return s;
    }

    public static List<String> stripAccents(List<String> s)
    {
        List<String> result = new ArrayList<>();
        for (String string: s){
            String tmp;
            tmp = Normalizer.normalize(string, Normalizer.Form.NFD);
            tmp = tmp.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
            tmp = tmp.replaceAll("\\s+$", "");
            result.add(tmp);
        }
        return result;
    }


}
