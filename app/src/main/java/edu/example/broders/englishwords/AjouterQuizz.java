package edu.example.broders.englishwords;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import utils.DataBaseHelper;

public class AjouterQuizz extends Activity {

    private DataBaseHelper myDb;
    Button creerQUIZZ;
    EditText editTextQUIZZ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_quizz_fragment);
        creerQUIZZ = findViewById(R.id.buttonValiderAddQuizz);
        editTextQUIZZ = findViewById(R.id.editQuizz);
        editTextQUIZZ.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        myDb = new DataBaseHelper(this);
        creerQUIZZ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextQUIZZ.getText().toString().equals("")){
                    Toast.makeText(AjouterQuizz.this, "Veuillez saisir le titre du quizz", Toast.LENGTH_LONG).show();
                    return;
                }
                //vérifier que le QUIZZ n'existe pas déjà.
                boolean res = myDb.ajouterUnQUIZZ(editTextQUIZZ.getText().toString().toLowerCase());
                if(res){
                    Toast.makeText(AjouterQuizz.this, "Quizz créé !", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(AjouterQuizz.this, "Une erreur est survenue lors de la création de ce quizz !", Toast.LENGTH_LONG).show();
                }
                    Intent intent = new Intent(AjouterQuizz.this,MesQuizz.class);
                    intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
            }
        });
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


}
