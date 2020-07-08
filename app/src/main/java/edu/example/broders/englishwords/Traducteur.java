package edu.example.broders.englishwords;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import utils.TraductionYandex;

public class Traducteur extends AppCompatActivity {

    TextView motATraduire,textViewTraduction,traduction,link;
    Button buttonTraduire,buttonAjouterAuRep,buttonSwapFromTo;
    EditText editTextMotATrad;
    String stateFromTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traducteur);
        motATraduire = (TextView) findViewById(R.id.textViewMotATrad);
        textViewTraduction = (TextView) findViewById(R.id.textViewTraduc2);
        traduction = (TextView) findViewById(R.id.traduction);
        link = (TextView) findViewById(R.id.textLink);
        link.setClickable(true);
        link.setMovementMethod(LinkMovementMethod.getInstance());
        stateFromTo="en-fr";
        String linkText = "<a href='http://translate.yandex.com/'>Yandex</a>";
        link.setText(Html.fromHtml(linkText));
        buttonTraduire = (Button) findViewById(R.id.buttonTrad);
        editTextMotATrad = (EditText) findViewById(R.id.edTxMotATrad);
        buttonAjouterAuRep = (Button) findViewById(R.id.buttonAddToRep);
        buttonSwapFromTo = (Button) findViewById(R.id.swap);

        buttonAjouterAuRep.setEnabled(false);
        buttonAjouterAuRep.setVisibility(View.INVISIBLE);
        editTextMotATrad.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        buttonTraduire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(v);
                List<String> motsATraduire = new ArrayList<>();
                motsATraduire.add(motATraduire.getText().toString());
                try {
                    traduction.setText(new TraductionYandex().execute(editTextMotATrad.getText().toString(),stateFromTo).get());
                    if(traduction.getText().length() >0){
                        buttonAjouterAuRep.setEnabled(true);
                        buttonAjouterAuRep.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        buttonAjouterAuRep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( Traducteur.this, AjouterUnMot.class );
                intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra( "MotAnglais", editTextMotATrad.getText().toString().toLowerCase() );
                intent.putExtra( "MotFrançais",traduction.getText().toString().toLowerCase() );
                startActivity( intent );

            }
        });

        buttonSwapFromTo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(Traducteur.this, "Marche pas", Toast.LENGTH_LONG).show();
                switch (stateFromTo){
                    case "en-fr" : motATraduire.setText("Français");
                                             textViewTraduction.setText("Anglais");
                                             stateFromTo = "fr-en";
                                             break;
                    case "fr-en" : motATraduire.setText("Anglais");
                                            textViewTraduction.setText("Français");
                                            stateFromTo = "en-fr";
                                             break;

                                             default:
                                                 System.out.println("Error switch From To");

                }
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        setContentView(R.layout.activity_traducteur);
        motATraduire = findViewById(R.id.textViewMotATrad);
        textViewTraduction = findViewById(R.id.textViewTraduc2);
        traduction = findViewById(R.id.traduction);
        buttonTraduire = findViewById(R.id.buttonTrad);
        editTextMotATrad = findViewById(R.id.edTxMotATrad);
        link = findViewById(R.id.textLink);
        link.setClickable(true);
        link.setMovementMethod(LinkMovementMethod.getInstance());
        String linkText = "<a href='http://translate.yandex.com/'>Yandex</a>";
        link.setText(Html.fromHtml(linkText));
        buttonAjouterAuRep = findViewById(R.id.buttonAddToRep);
        buttonAjouterAuRep.setEnabled(false);
        buttonAjouterAuRep.setVisibility(View.INVISIBLE);
        editTextMotATrad.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        buttonTraduire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(v);
                List<String> motsATraduire = new ArrayList<>();
                motsATraduire.add(motATraduire.getText().toString());
                try {
                    traduction.setText(new TraductionYandex().execute(editTextMotATrad.getText().toString(),stateFromTo).get());
                    if(traduction.getText().length() >0){
                        buttonAjouterAuRep.setEnabled(true);
                        buttonAjouterAuRep.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        buttonAjouterAuRep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( Traducteur.this, AjouterUnMot.class );
                intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra( "MotAnglais", editTextMotATrad.getText().toString().toLowerCase() );
                intent.putExtra( "MotFrançais",traduction.getText().toString().toLowerCase() );
                startActivity( intent );
                finish();

            }
        });

        buttonSwapFromTo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                switch (stateFromTo){
                    case "en-fr" : motATraduire.setText("Français");
                        textViewTraduction.setText("Anglais");
                        stateFromTo = "fr-en";
                        break;
                    case "fr-en" : motATraduire.setText("Anglais");
                        textViewTraduction.setText("Français");
                        stateFromTo = "en-fr";
                        break;

                    default:
                        System.out.println("Error switch From To");

                }
            }
        });

    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void setSwap(View view) {
        switch (stateFromTo){
            case "en-fr" : motATraduire.setText("Français");
                textViewTraduction.setText("Anglais");
                stateFromTo = "fr-en";
                break;
            case "fr-en" : motATraduire.setText("Anglais");
                textViewTraduction.setText("Français");
                stateFromTo = "en-fr";
                break;

            default:
                System.out.println("Error switch From To");

        }
    }
}
