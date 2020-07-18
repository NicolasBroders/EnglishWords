package edu.example.broders.englishwords;

import android.app.Activity;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import utils.DataBaseHelper;

public class ModifierQuizz extends AppCompatActivity {
    private DataBaseHelper myDb;
    Button buttonModifierQuizz;
    EditText quiz;
    String ancienQuizz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier_quizz);
        final Intent intent = getIntent();
        ancienQuizz = intent.getStringExtra( "ancienNomQuizz" ).toString();
        buttonModifierQuizz = findViewById( R.id.buttonValiderModifierQuizz );
        quiz = findViewById( R.id.editQuizzModif);
        quiz.setText(ancienQuizz);
        quiz.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        myDb = new DataBaseHelper(this);
        List<String> listVocab = new ArrayList<String>();

        buttonModifierQuizz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //verifier que tout les champs sont remplies
                if(quiz.getText().toString().equals("")){
                    Toast.makeText(ModifierQuizz.this, "Veuillez saisir le nom du quizz", Toast.LENGTH_LONG).show();
                    return;
                }
                //vérifier que les mots n'existes pas déjà ( même mot en anglais dans un même contexte )
                boolean res = myDb.modifierQuizz(ancienQuizz,quiz.getText().toString().toLowerCase());
                if(res){
                    Toast.makeText(ModifierQuizz.this, "Modification effectuée !", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ModifierQuizz.this,MesQuizz.class);
                    intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }else{
                    Toast.makeText(ModifierQuizz.this, "Une erreur est survenue lors de la modification !", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
