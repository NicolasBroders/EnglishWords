package edu.example.broders.englishwords;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import edu.example.broders.englishwords.home.HomeFragment;

public class EndGame extends Activity {

    private TextView firstStrikeScore;
    private TextView errorScore;
    private TextView scoreTotal;
    private Button retourMenu;
    private int strikeScore,errors,found,total;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.end_game_fragment);
        Intent intent = getIntent();
        firstStrikeScore = findViewById( R.id.scoreFirstStrike);
        errorScore = findViewById( R.id.scoreError );
        scoreTotal = findViewById( R.id.scoreTotal );
        retourMenu = findViewById( R.id.retourMenu );
        found = intent.getIntExtra( "found",0 );
        strikeScore=intent.getIntExtra( "scoreFirstStrike",0 );
        errors = intent.getIntExtra( "scoreError",0 );
        total = found + (strikeScore * 3) - errors;
        if (total < 0){
            total = 0 ;
        }
        firstStrikeScore.setText( "Mots trouvés du premier coup : "+strikeScore);
        errorScore.setText( "Erreurs faites : "+errors);
        scoreTotal.setText( "Score total : " +total);
        retourMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Question.scoreFirstStrike = 0 ;
                Question.totalError = 0;
                Question.found = 0;
                Intent intent = new Intent(EndGame.this, HomeFragment.class);
                intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }

}
