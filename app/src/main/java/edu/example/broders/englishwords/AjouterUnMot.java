package edu.example.broders.englishwords;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import utils.DataBaseHelper;

public class AjouterUnMot extends Activity {

    private DataBaseHelper myDb;
    Button buttonAjouterMot;
    FloatingActionButton buttonAddTheme;
    String motAnglaisFromTrad="";
    String motFrancaisFromTrad="";
    EditText motAnglais,motFrancais;
    Spinner spinnerQuizz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_un_mot);
        final Intent intent = getIntent();
        if(intent.hasExtra("MotAnglais")){
            motAnglaisFromTrad = intent.getStringExtra( "MotAnglais" ).toString();
        }
        if(intent.hasExtra("MotFrançais")){
            motFrancaisFromTrad = intent.getStringExtra( "MotFrançais" ).toString();
        }
        buttonAjouterMot = findViewById( R.id.buttonAjouterMot );
        motAnglais = findViewById( R.id.editTAnglais );
        motAnglais.setText(motAnglaisFromTrad);
        motFrancais = findViewById( R.id.editTextFr );
        motFrancais.setText(motFrancaisFromTrad);
        spinnerQuizz = findViewById(R.id.spinnerQuizz) ;
        myDb = new DataBaseHelper(this);
        List<String> listVocab = new ArrayList<String>();
        Cursor resul = myDb.getAllQUIZZs();
        motFrancais.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        motAnglais.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        spinnerQuizz.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        if(resul.getCount()==0){
            Toast.makeText(AjouterUnMot.this, "Pas de thème créé !", Toast.LENGTH_LONG).show();
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
        buttonAjouterMot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //verifier que tout les champs sont remplies
                if(motAnglais.getText().toString().equals("")){
                    Toast.makeText(AjouterUnMot.this, "Veuillez saisir un mot en anglais", Toast.LENGTH_LONG).show();
                    return;
                }
                if(motFrancais.getText().toString().equals("")){
                    Toast.makeText(AjouterUnMot.this, "Veuillez saisir un mot en français", Toast.LENGTH_LONG).show();
                    return;
                }
                if(spinnerQuizz.getSelectedItem().toString().equals("")){
                    Toast.makeText(AjouterUnMot.this, "Commencez par créer un thème", Toast.LENGTH_LONG).show();
                    return;
                }
                //vérifier que les mots n'existes pas déjà ( même mot en anglais dans un même contexte )
                boolean res = myDb.ajouterUnMot(motAnglais.getText().toString().toLowerCase(),
                                                motFrancais.getText().toString().toLowerCase(),
                                                myDb.getIdQUIZZ(spinnerQuizz.getSelectedItem().toString()));
                if(res){
                    Toast.makeText(AjouterUnMot.this, "Mot ajouté au répertoire !", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(AjouterUnMot.this,Repertoire.class);
                    intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(AjouterUnMot.this, "Une erreur est survenue lors de l'ajout !", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
