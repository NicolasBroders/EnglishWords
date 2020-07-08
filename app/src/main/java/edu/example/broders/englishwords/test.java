package edu.example.broders.englishwords;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import utils.DataBaseHelper;

public class test extends AppCompatActivity {

    private Button start;
    private int motRestant;
    private DataBaseHelper myDb;
    private boolean jouable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gridlayout);
        final Spinner spinnerLangue = findViewById( R.id.spinnerLangue );
        ArrayAdapter<CharSequence> adapterLangue = ArrayAdapter.createFromResource( this,
                R.array.langue_array, android.R.layout.simple_spinner_item);
        adapterLangue.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        // Apply the adapter to the spinner
        spinnerLangue.setAdapter(adapterLangue);

        final Spinner spinnerQuizz = (Spinner) findViewById(R.id.spinnerQuizz);
        myDb = new DataBaseHelper(this);
        List<String> listVocab = new ArrayList<String>();
        Cursor resul = myDb.getAllQUIZZs();
        if(resul.getCount()==0){
            Toast.makeText(test.this, "Pas de thème créé !", Toast.LENGTH_LONG).show();
        }else {
            while (resul.moveToNext()) {
                listVocab.add(resul.getString(1));
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_list_item_1,
                    listVocab );

            spinnerQuizz.setAdapter(arrayAdapter);
        }

        final Spinner spinnerWordCount = (Spinner) findViewById(R.id.spinnerWordCount);
        ArrayAdapter<CharSequence> adapterWordCount = ArrayAdapter.createFromResource( this,
                R.array.WordCount_array, android.R.layout.simple_spinner_item);
        adapterWordCount.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        // Apply the adapter to the spinner
        spinnerWordCount.setAdapter(adapterWordCount);
    /* Specify the layout to use when the list of choices appears */

        start = (Button) findViewById(R.id.LancezPartie);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                jouable = !(myDb.isQuizzEmpty(myDb.getIdQUIZZ(spinnerQuizz.getSelectedItem().toString())));
                if(!jouable) {
                    Toast.makeText(test.this, "Ce quizz ne contient aucun mot", Toast.LENGTH_LONG).show();
                }else{
                    Cursor res = myDb.getAllWordsOfTheQUIZZ(spinnerQuizz.getSelectedItem().toString());
                    List<String> motsFr = new ArrayList<>();
                    List<String> motsEn = new ArrayList<>();
                    List<String> contextes = new ArrayList<>();
                    if(res.getCount()==0){
                        Toast.makeText(test.this, "Pas de mot trouvé pour ce quizz !", Toast.LENGTH_LONG).show();
                    }else {
                        while (res.moveToNext()) {
                            motsFr.add(res.getString(1));
                            motsEn.add(res.getString(2));
                            contextes.add(res.getString(3));
                        }
                    }
                    BoiteAQuestion.BoiteAQuestion(motsFr,motsEn,contextes);
                    motRestant = Integer.parseInt(spinnerWordCount.getSelectedItem().toString());
                    Intent intent = new Intent(test.this,Question.class);
                    intent.putExtra( "theme_questions",spinnerQuizz.getSelectedItem().toString() );
                    intent.putExtra( "wordCount" ,motRestant);
                    intent.putExtra( "choixLangue",spinnerLangue.getSelectedItem().toString() );
                    startActivity(intent);
                }
            }

        });
    }



}
