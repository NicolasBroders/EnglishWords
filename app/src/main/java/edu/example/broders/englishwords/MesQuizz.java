package edu.example.broders.englishwords;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
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

public class MesQuizz extends AppCompatActivity {

    private ListView lvWords;
    private DataBaseHelper myDb;
    private ArrayList<String> data = new ArrayList<>();
    Spinner SpinnerOrdre;
    private Toast myToast;

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_quizz,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.AddQuizzMenu:
                Intent intent = new Intent(MesQuizz.this,AjouterQuizz.class);
                startActivity(intent);
                break;
            case R.id.GiftMenu:
                showAToast("Merci !");
                break;
        }

        return true;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_mes_quizz );
        myDb = new DataBaseHelper(this);
        generateQuizzByName();
        SpinnerOrdre = findViewById(R.id.spinnerOrdre);
        ArrayAdapter<CharSequence> adapterOrdre = ArrayAdapter.createFromResource( this,
                R.array.Ordre, android.R.layout.simple_spinner_item);
        adapterOrdre.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        SpinnerOrdre.setAdapter(adapterOrdre);
        lvWords= findViewById(R.id.listViewWords);
        lvWords.setAdapter(new MesQuizz.MyListAdapter(this,R.layout.nodeword,data));

        SpinnerOrdre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    //ordre croissant
                    case 0:
                        generateQuizzByName();
                        lvWords.invalidateViews();
                        lvWords.setAdapter(new MesQuizz.MyListAdapter(MesQuizz.this,R.layout.nodeword,data));
                        break;

                    //ordre décroissant
                    case 1:
                        generateQuizzByDesc();
                        lvWords.invalidateViews();
                        lvWords.setAdapter(new MesQuizz.MyListAdapter(MesQuizz.this,R.layout.nodeword,data));
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                // sometimes you need nothing here
            }
        });
    }

    public void generateQuizzByName(){
        data.clear();
        Cursor resul = myDb.getAllQUIZZs();
        if(resul.getCount()==0){
            showAToast("Pas de quizz !");
        }else {
            while (resul.moveToNext()) {
                data.add(resul.getString(1));
            }
        }
    }

    public void generateQuizzByDesc(){
        data.clear();
        Cursor resul = myDb.getAllQUIZZDesc();
        if(resul.getCount()==0){
            showAToast("Pas de quizz !");
        }else {
            while (resul.moveToNext()) {
                data.add(resul.getString(1));
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
            MesQuizz.MyListAdapter.ViewHolder viewHolder = new MesQuizz.MyListAdapter.ViewHolder();

            viewHolder.quizz = (TextView) convertView.findViewById(R.id.textViewNode);
            viewHolder.quizz.setText(objects.get(position));

            viewHolder.editQuizz = (Button) convertView.findViewById(R.id.buttonEditNode);
            viewHolder.editQuizz.setOnClickListener(new View.OnClickListener() {

                public void onClick(View view) {
                    View parentRow = (View) view.getParent();
                    ListView listView = (ListView) parentRow.getParent();
                    final int position = listView.getPositionForView(parentRow);
                    String ancienNomQuizz = objects.get(position);
                    Intent intent = new Intent( MesQuizz.this, ModifierQuizz.class );
                    intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra( "ancienNomQuizz", ancienNomQuizz );
                    startActivity( intent );
                }
            });

            viewHolder.suppressQuizz = (Button) convertView.findViewById(R.id.buttonDelNode);
            viewHolder.suppressQuizz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    View parentRow = (View) view.getParent();
                    ListView listView = (ListView) parentRow.getParent();
                    final int position = listView.getPositionForView(parentRow);
                    final String quizzASupp = objects.get(position);
                    new AlertDialog.Builder(MesQuizz.this)

                            .setTitle("Quizz")
                            .setMessage("Voulez vous supprimer le quizz "+quizzASupp+" de vos quizz ? ")
                            .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    //if(myDb.supprimerQuizz(quizzASupp)){
                                    //    reloadData();
                                    //    showAToast("Quizz supprimé");
                                    //}else{
                                    //    showAToast("Problème lors de la suppression du quizz");
                                    //}
                                    showAToast("En cours de développement");
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
            TextView quizz;

            Button editQuizz;
            Button suppressQuizz;
        }
    }

    public void reloadData(){
        int positionSpinnerOrdre = SpinnerOrdre.getSelectedItemPosition();
        switch (positionSpinnerOrdre) {
            case 0:
                    //trier par ordre croissant
                generateQuizzByName();
                lvWords.setAdapter(new MesQuizz.MyListAdapter(MesQuizz.this, R.layout.nodeword, data));
                lvWords.invalidateViews();
                SpinnerOrdre.setSelection(0);
                break;
                    //trier par ordre décroissant
            case 1:
                generateQuizzByDesc();
                lvWords.setAdapter(new MesQuizz.MyListAdapter(MesQuizz.this, R.layout.nodeword, data));
                lvWords.invalidateViews();
                SpinnerOrdre.setSelection(0);
                break;

                default: return;
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

