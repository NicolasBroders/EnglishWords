package edu.example.broders.englishwords;

import java.util.ArrayList;
import java.util.List;

public class BoiteAQuestion {

    static List<String> wordFr = new ArrayList<>();
    static List<String> wordEn = new ArrayList<>();
    static List<String> contex = new ArrayList<>();
    static int numeroQuestion = 0;
    static int tailleQuizz;

    public static void BoiteAQuestion(List<String> motsFr, List<String> motsEn, List<String> contextes){
        wordFr = motsFr;
        wordEn = motsEn;
        contex = contextes;
        tailleQuizz = motsFr.size();
    }

    public static void supprimerQuestion(){
        wordFr.remove(numeroQuestion);
        wordEn.remove(numeroQuestion);
        contex.remove(numeroQuestion);
    }

    public static int tailleDeLaBoite(){
        return wordEn.size();
    }

    public static String recupMotFr(){
        return wordFr.get(numeroQuestion);
    }



    public static String recupMotEn(){
        return wordEn.get(numeroQuestion);
    }

    public static String recupContext(){
        return contex.get(numeroQuestion);
    }
}
