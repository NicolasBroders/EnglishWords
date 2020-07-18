package edu.example.broders.englishwords.home;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import edu.example.broders.englishwords.MesQuizz;
import edu.example.broders.englishwords.R;
import edu.example.broders.englishwords.Repertoire;
import edu.example.broders.englishwords.Traducteur;
import edu.example.broders.englishwords.QuizzSetting;
import utils.DataBaseHelper;

public class Home extends AppCompatActivity {

    private Button jouer;
    private Button repertoire;
    private Button buttonTraducteur;
    private Button buttonMesQuizz;
    private DataBaseHelper myDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        myDb = new DataBaseHelper(this);
        jouer = findViewById(R.id.play_btn);
        repertoire = findViewById( R.id.repertoire );
        buttonMesQuizz = findViewById(R.id.buttonMesQuizz);
        buttonTraducteur = findViewById(R.id.buttonTraducteur);
        jouer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean jouable = !(myDb.noQuizzCreated());
                if(jouable){
                    Intent intent = new Intent(Home.this, QuizzSetting.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(Home.this, "Vous n'avez pas encore créé de quizz", Toast.LENGTH_LONG).show();
                }

            }
        });

        repertoire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Repertoire.class);
                startActivity(intent);
            }
        });

        buttonMesQuizz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, MesQuizz.class);
                startActivity(intent);
            }
        });

        buttonTraducteur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(Home.this, "En cours de développement", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Home.this, Traducteur.class);
                startActivity(intent);
            }
        });

    }
}
