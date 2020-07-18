package edu.example.broders.englishwords;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import utils.DataBaseHelper;

public class Repertoire extends AppCompatActivity {

    private ListView lvWords;
    private DataBaseHelper myDb;
    private ArrayList<String> data = new ArrayList<>();
    Spinner SpinnerOrdre;
    Spinner SpinnerTrierPar;
    private Toast myToast;

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_repertoire,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.AddWordMenu:
                boolean jouable = !(myDb.noQuizzCreated());
                if(jouable){
                    Intent intent = new Intent(Repertoire.this,AjouterUnMot.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(Repertoire.this, "Commencez par créer un quizz", Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.GiftMenu:
                showAToast("Merci !");
                break;
        }

        return true;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_repertoire );
        myDb = new DataBaseHelper(this);
        generateDataByName();
        SpinnerTrierPar = findViewById(R.id.spinnerTrierPar);
        ArrayAdapter<CharSequence> adapterTrier = ArrayAdapter.createFromResource( this,
                R.array.Trier_par, android.R.layout.simple_spinner_item);
        adapterTrier.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        SpinnerTrierPar.setAdapter(adapterTrier);
        SpinnerOrdre = findViewById(R.id.spinnerOrdre);
        ArrayAdapter<CharSequence> adapterOrdre = ArrayAdapter.createFromResource( this,
                R.array.Ordre, android.R.layout.simple_spinner_item);
        adapterOrdre.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        SpinnerOrdre.setAdapter(adapterOrdre);
        lvWords= findViewById(R.id.listViewWords);
        lvWords.setAdapter(new MyListAdapter(this,R.layout.nodeword,data));

        SpinnerTrierPar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    //trier par nom
                    case 0:
                            generateDataByName();
                            lvWords.setAdapter(new MyListAdapter(Repertoire.this,R.layout.nodeword,data));
                            lvWords.invalidateViews();
                            SpinnerOrdre.setSelection(0);
                            break;
                    //trier par score
                    case 1:
                            generateDataByScore();
                            lvWords.setAdapter(new MyListAdapter(Repertoire.this,R.layout.nodeword,data));
                            lvWords.invalidateViews();
                            SpinnerOrdre.setSelection(1);
                            break;
                    default:
                            break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                // sometimes you need nothing here
            }
        });

        SpinnerOrdre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    //ordre croissant
                    case 0:
                        switch (SpinnerTrierPar.getSelectedItemPosition()){
                            //nom
                            case 0:
                                generateDataByName();
                                lvWords.setAdapter(new MyListAdapter(Repertoire.this,R.layout.nodeword,data));
                                lvWords.invalidateViews();
                                SpinnerOrdre.setSelection(0);
                                break;
                                //score
                            case 1:
                                generateDataByScoreAsc();
                                lvWords.setAdapter(new MyListAdapter(Repertoire.this,R.layout.nodeword,data));
                                lvWords.invalidateViews();
                                SpinnerOrdre.setSelection(0);
                                break;
                        }
                        break;
                    //ordre décroissant
                    case 1:
                        switch (SpinnerTrierPar.getSelectedItemPosition()){
                            //nom
                            case 0:
                                generateDataByNameDesc();
                                lvWords.setAdapter(new MyListAdapter(Repertoire.this,R.layout.nodeword,data));
                                lvWords.invalidateViews();
                                SpinnerOrdre.setSelection(1);
                                break;
                                //score
                            case 1:
                                generateDataByScore();
                                lvWords.setAdapter(new MyListAdapter(Repertoire.this,R.layout.nodeword,data));
                                lvWords.invalidateViews();
                                SpinnerOrdre.setSelection(1);
                                break;

                        }
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                // sometimes you need nothing here
            }
        });
    }

    public void generateDataByName(){
        data.clear();
        Cursor resul = myDb.getAllWordsByName();
        if(resul.getCount()==0){
            showAToast("Pas de mot dans le repertoire !");
        }else {
            while (resul.moveToNext()) {
                data.add("Mot anglais : " + resul.getString(2) + "\n"
                        + "Mot français : " + resul.getString(1) + "\n"
                        + "Score : " + resul.getString(4));
            }
        }
    }

    public void generateDataByNameDesc(){
        data.clear();
        Cursor resul = myDb.getAllWordsByNameDesc();
        if(resul.getCount()==0){
            showAToast("Pas de mot dans le repertoire !");
        }else {
            while (resul.moveToNext()) {
                data.add("Mot anglais : " + resul.getString(2) + "\n"
                        + "Mot français : " + resul.getString(1) + "\n"
                        + "Contexte : " + resul.getString(3) + "\n"
                        + "Score : " + resul.getString(4));
            }
        }
    }

    public void generateDataByScore(){
        data.clear();
        Cursor resul = myDb.getAllWordsByScore();
        if(resul.getCount()==0){
            showAToast("Pas de mot dans le repertoire !");
        }else {
            while (resul.moveToNext()) {
                data.add("Mot anglais : " + resul.getString(2) + "\n"
                        + "Mot français : " + resul.getString(1) + "\n"
                        + "Score : " + resul.getString(4));
            }
        }
    }

    public void generateDataByScoreAsc(){
        data.clear();
        Cursor resul = myDb.getAllWordsByScoreAsc();
        if(resul.getCount()==0){
            showAToast("Pas de mot dans le repertoire !");
        }else {
            while (resul.moveToNext()) {
                data.add("Mot anglais : " + resul.getString(2) + "\n"
                        + "Mot français : " + resul.getString(1) + "\n"
                        + "Score : " + resul.getString(4));
            }
        }
    }

    private class MyListAdapter extends ArrayAdapter<String> {
        private int layout;
        private List<String> objects;
        int currentPosition;
        private MyListAdapter(Context context, int ressource , List<String> objects){
            super(context,ressource,objects);
            layout = ressource;
            this.objects=objects;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            currentPosition = position;
            if(convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
            }
                ViewHolder viewHolder = new ViewHolder();

                viewHolder.word = (TextView) convertView.findViewById(R.id.textViewNode);
                viewHolder.word.setText(objects.get(position));

                viewHolder.editWord = (Button) convertView.findViewById(R.id.buttonEditNode);
                viewHolder.editWord.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View view) {
                        View parentRow = (View) view.getParent();
                        ListView listView = (ListView) parentRow.getParent();
                        final int position = listView.getPositionForView(parentRow);
                        String allAbout = objects.get(position);
                        String[] allString = allAbout.split(":");
                        String ancienMotAnglais = allString[1].substring(1,allString[1].indexOf("Mot")-1);
                        String ancienMotFrançais = allString[2].substring(1,allString[2].indexOf("Score")-1);
                        Intent intent = new Intent( Repertoire.this, ModifierMot.class );
                        intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra( "ancienMotAnglais", ancienMotAnglais );
                        intent.putExtra( "ancienMotFrançais",ancienMotFrançais );
                        startActivity( intent );
                    }
                });

                viewHolder.suppressWord = (Button) convertView.findViewById(R.id.buttonDelNode);
                viewHolder.suppressWord.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        View parentRow = (View) view.getParent();
                        ListView listView = (ListView) parentRow.getParent();
                        final int position = listView.getPositionForView(parentRow);
                        String allAbout = objects.get(position);
                        String[] allString = allAbout.split(":");
                        final String motASupp = allString[1].substring(1,allString[1].indexOf("Mot")-1);
                        new AlertDialog.Builder(Repertoire.this)

                                .setTitle("Répertoire")
                                .setMessage("Voulez vous supprimer le mot "+motASupp+" du répertoire ? ")
                                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        if(myDb.supprimerMot(motASupp)){
                                            reloadData();
                                            showAToast("Mot supprimé");
                                        }else{
                                            showAToast("Problème lors de la suppression du mot");
                                        }

                                        dialog.dismiss();
                                    }
                                }).setNegativeButton("Non", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // The user is not sure, so you can exit or just stay
                                dialog.dismiss();
                            }
                        }).show();
                    }
                });

                convertView.setTag(viewHolder);
            return convertView;
        }
        public class ViewHolder {
            TextView word;

            Button editWord;
            Button suppressWord;
        }
    }

    public void reloadData(){
        int positionSpinnerTrierPar = SpinnerTrierPar.getSelectedItemPosition();
        int positionSpinnerOrdre = SpinnerOrdre.getSelectedItemPosition();
        switch (positionSpinnerTrierPar) {
            //trier par nom
            case 0:
                switch (positionSpinnerOrdre) {
                    //trier par ordre croissant
                    case 0:
                        generateDataByName();
                        lvWords.setAdapter(new MyListAdapter(Repertoire.this, R.layout.nodeword, data));
                        lvWords.invalidateViews();
                        SpinnerOrdre.setSelection(0);
                        break;
                    //trier par ordre décroissant
                    case 1:
                        generateDataByNameDesc();
                        lvWords.setAdapter(new MyListAdapter(Repertoire.this, R.layout.nodeword, data));
                        lvWords.invalidateViews();
                        SpinnerOrdre.setSelection(0);
                        break;

                        default: return;
                }
            //trier par score
            case 1:
                switch (positionSpinnerOrdre) {
                    //trier par ordre croissant
                    case 0:
                        generateDataByScoreAsc();
                        lvWords.setAdapter(new MyListAdapter(Repertoire.this, R.layout.nodeword, data));
                        lvWords.invalidateViews();
                        SpinnerOrdre.setSelection(0);
                        break;
                    //trier par ordre décroissant
                    case 1:
                        generateDataByScore();
                        lvWords.setAdapter(new MyListAdapter(Repertoire.this, R.layout.nodeword, data));
                        lvWords.invalidateViews();
                        SpinnerOrdre.setSelection(0);
                        break;

                    default: return;
                }
        }
    }

    public void showAToast (String message){
        if (myToast != null) {
            myToast.cancel();
        }
        myToast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        myToast.show();
    }

}
