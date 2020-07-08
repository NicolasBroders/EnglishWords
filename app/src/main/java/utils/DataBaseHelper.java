package utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHelper extends SQLiteOpenHelper {

    private final static String DATABASE_NAME = "englishword.db";

    private final static String TABLE_REPERTOIRE = "repertoire";
    private final static String COL_0 = "ID";
    private final static String COL_1 = "FR";
    private final static String COL_2 = "EN";
    //private final static String COL_3 = "CONTEXT";
    private final static String COL_3 = "SCORE";
    private final static String COL_4 = "ID_QUIZZ";

    private final static String TABLE_QUIZZ = "quizz";
    private final static String COL_0_QUIZZ = "ID";
    private final static String COL_1_QUIZZ = "QUIZZ";


    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_REPERTOIRE+ " (ID INTEGER PRIMARY KEY AUTOINCREMENT, FR TEXT, EN TEXT,SCORE INTEGER,ID_QUIZZ INTEGER)");
        db.execSQL("create table "+TABLE_QUIZZ+ " (ID INTEGER PRIMARY KEY AUTOINCREMENT, QUIZZ TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_REPERTOIRE);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_QUIZZ);
        onCreate(db);
    }

    public boolean ajouterUnMot(String motAnglais, String motFrancais ,int id_QUIZZ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,motFrancais);
        contentValues.put(COL_2,motAnglais);
        contentValues.put(COL_3,0);
        contentValues.put(COL_4,id_QUIZZ);
        if(db.insert(TABLE_REPERTOIRE,null,contentValues) == -1){
            return false;
        }
        return true;
    }

    public boolean ajouterUnQUIZZ(String QUIZZ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1_QUIZZ,QUIZZ);
        if(db.insert(TABLE_QUIZZ,null,contentValues) == -1){
            return false;
        }
        return true;
    }

    public Cursor getAllWordsByName(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_REPERTOIRE+" order by "+COL_2,null);
        return res;
    }

    public Cursor getAllWordsByNameDesc(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_REPERTOIRE+" order by "+COL_2+" DESC",null);
        return res;
    }

    public Cursor getAllWordsByScore(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_REPERTOIRE+" order by "+COL_3+" DESC",null);
        return res;
    }

    public Cursor getAllWordsByScoreAsc() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_REPERTOIRE+" order by "+COL_3+" ASC",null);
        return res;
    }


    public Cursor getAllQUIZZs(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_QUIZZ+" order by "+COL_1_QUIZZ,null);
        return res;
    }

    public int getIdQUIZZ(String QUIZZ){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_QUIZZ+" where "+COL_1_QUIZZ+" = \'"+QUIZZ+"\'",null);
        res.moveToNext();
        return (Integer.parseInt(res.getString(0)));
    }

    public Cursor getAllQUIZZDesc() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_QUIZZ+" order by "+COL_1_QUIZZ+" DESC",null);
        return res;
    }

    public Cursor getAllWordsOfTheQUIZZ(String QUIZZ){
        int QUIZZ_id=getIdQUIZZ(QUIZZ);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_REPERTOIRE+" where "+COL_4+" = "+ QUIZZ_id+" order by "+COL_3+" ASC",null);
        return res;
    }

    public int getIdWord(String word,String col){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_REPERTOIRE+" where "+col+" = \""+word+"\"",null);

        res.moveToNext();
        return (Integer.parseInt(res.getString(0)));
    }

    public void upScore(String word,String choixLangue){
        String langue;
        int idWord;
        if(choixLangue.equals("Anglais → Français")){
            langue=COL_1;
        }else{
            langue=COL_2;
        }
        idWord = getIdWord(word,langue);
        SQLiteDatabase db = this.getWritableDatabase();
        Log.i("UP SCORE",word);
        String strSQL = "UPDATE repertoire SET SCORE = SCORE + 1 WHERE "+COL_0+" = "+ idWord;
        db.execSQL(strSQL);
    }

    public boolean modifierMot(String ancienMotAnglais, String nouveauMotAnglais, String nouveauMotFrançais,int nouveauIdQuizz) {
        int idWord;
        idWord = getIdWord(ancienMotAnglais,COL_2);
        Log.i("Modification du mot",ancienMotAnglais);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,nouveauMotFrançais);
        contentValues.put(COL_2,nouveauMotAnglais);
        contentValues.put(COL_4,nouveauIdQuizz);
        if(db.update(TABLE_REPERTOIRE,contentValues,COL_0+" = "+ idWord,null) == 0){
            return false;
        }
        return true;
    }

    public boolean supprimerMot(String motAnglais) {
        Log.i("Suppression du mot",motAnglais);
        int idWord;
        idWord = getIdWord(motAnglais,COL_2);
        SQLiteDatabase db = this.getWritableDatabase();
        if(db.delete(TABLE_REPERTOIRE,COL_0+" = "+ idWord,null) == 0){
            return false;
        }
        return true;
    }

    public boolean supprimerQuizz(String quizzASupp){
        Log.i("Suppression du quizz",quizzASupp);
        int idQuizz;
        idQuizz = getIdWord(quizzASupp,COL_2);
        SQLiteDatabase db = this.getWritableDatabase();
        if(db.delete(TABLE_REPERTOIRE,COL_0+" = "+ quizzASupp,null) == 0){
            return false;
        }
        return true;
    }

    public boolean modifierQuizz(String ancienQuizz, String nouveauQuizz) {
        int idQuizz;
        idQuizz = getIdQUIZZ(ancienQuizz);
        Log.i("Modification du mot",ancienQuizz);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1_QUIZZ,nouveauQuizz);
        if(db.update(TABLE_QUIZZ,contentValues,COL_0_QUIZZ+" = "+ idQuizz,null) == 0){
            return false;
        }
        return true;
    }

    public boolean isQuizzEmpty(int idQuizz){
        SQLiteDatabase db = this.getWritableDatabase();
        String count = "SELECT count(*) FROM "+TABLE_REPERTOIRE+" WHERE "+COL_4+" = "+idQuizz;
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        if(icount>0){
            return false;
        }else{
            return true;
        }
    }

    public boolean noQuizzCreated(){
        SQLiteDatabase db = this.getWritableDatabase();
        String count = "SELECT count(*) FROM "+TABLE_QUIZZ;
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        if(icount>0){
            return false;
        }else{
            return true;
        }
    }




}
