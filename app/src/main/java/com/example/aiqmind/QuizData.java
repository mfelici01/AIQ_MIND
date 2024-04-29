package com.example.aiqmind;

import android.content.Context;
import android.content.res.Resources;

import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class QuizData implements Parcelable {
    //Pour stocker stocker le numero de la derniere/grande question vu
    //en repondant au questionnaire
    private int currentQuestion;
    //Pour stocker le numéro de la question sur laquelle on est
    private int actualPageQuestion;

    //Stocker le Score
    private int score;

    //Stocker les reponses du joueur
    private List<List<Integer>> quizTab;


    public QuizData() {
        currentQuestion = 1;
        actualPageQuestion = 1;
        score = 0;
        quizTab = new ArrayList<>();
    }

    private QuizData(Parcel in) {
        currentQuestion = in.readInt();
        actualPageQuestion = in.readInt();
        score = in.readInt();

        int size = in.readInt();
        quizTab = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            List<Integer> answers = new ArrayList<>();
            in.readList(answers, Integer.class.getClassLoader());
            quizTab.add(answers);
        }
    }

    //Recuperation du nombre de question repondu
    public int getQuizSize(){
        return quizTab.size();
    }

    public static final Creator<QuizData> CREATOR = new Creator<QuizData>() {
        @Override
        public QuizData createFromParcel(Parcel in) {
            return new QuizData(in);
        }

        @Override
        public QuizData[] newArray(int size) {
            return new QuizData[size];
        }
    };

    public int getActualPageQuestion(){
        return actualPageQuestion;
    }

    public int getCurrentQuestion() {
        return currentQuestion;
    }

    //Pour update le numéro de la question sur laquelle on est en cas de retour en arriere
    public void backActualPageQuestion(){
        if (actualPageQuestion > 0){
            actualPageQuestion--;
        }
    }

    public int getScore(){
        return score;
    }

    //Augmenter le score
    public void addScore(){
        score++;
    }

    //Update le numero de la question sur la quelle on est
    public void nextActualPageQuestion(){
        actualPageQuestion++;
    }

    //Update le numero de la dernere question vu
    public void nextCurrentQuestion(){
        if(currentQuestion == actualPageQuestion){
            currentQuestion++;
        }
    }

    //Recuperer la reponse du joueur
    public List<Integer> getQuizAnswer(int question) {
        return quizTab.get(question - 1);
    }


    //update la reponse du joueur
    public void addQuizAnswer(int answer) {
        if (currentQuestion > quizTab.size()) {
            quizTab.add(new ArrayList<>());
        }

        quizTab.get(currentQuestion - 1).add(answer);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(currentQuestion);
        dest.writeInt(actualPageQuestion);
        dest.writeInt(score);

        dest.writeInt(quizTab.size());
        for (List<Integer> answers : quizTab) {
            dest.writeList(answers);
        }
    }

    public void writeQuizDataToFile(Context context, String userName) {
        File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File fileout = new File(folder, userName + "_Game_Answer.txt");

        try (FileOutputStream fos = new FileOutputStream(fileout);
             PrintStream ps = new PrintStream(fos)) {

            Resources resources = context.getResources();

            ps.println("Score Total: " + getScore());
            ps.println();
            int n;

            //1er question
            ps.println("Answer " + resources.getString(R.string.question1));
            List<Integer> reponse = getQuizAnswer(1);
            for(int i = 0; i < reponse.size(); i++){
                n = reponse.get(i);
                if (n == 1){
                    ps.println(" > " + resources.getString(R.string.question1R1));
                }
                if (n == 2){
                    ps.println(" > " + resources.getString(R.string.question1R2));
                }
                if (n == 3){
                    ps.println(" > " + resources.getString(R.string.question1R3));
                }
                if (n == 4){
                    ps.println(" > " + resources.getString(R.string.question1R4));
                }
            }
            ps.println();

            //2eme question
            ps.println("Answer " + resources.getString(R.string.question2));
            reponse = getQuizAnswer(2);
            ps.println(" > Logo " + reponse.get(0));
            ps.println();

            //3eme question
            ps.println("Answer " + resources.getString(R.string.question3));
            reponse = getQuizAnswer(3);
            ps.println(" > " + reponse.get(0) + "/" + reponse.get(1) + "/" + reponse.get(2));
            ps.println();

            //4eme question
            ps.println("Answer " + resources.getString(R.string.question4));
            reponse = getQuizAnswer(4);
            ps.println(" > " + reponse.get(0));
            ps.println();

            //5eme question
            ps.println("Answer " + resources.getString(R.string.question5));
            reponse = getQuizAnswer(5);
            if(reponse.get(0) == 1){
                ps.println(" > " + resources.getString(R.string.question5R1));
            } else if(reponse.get(0) == 2){
                ps.println(" > " + resources.getString(R.string.question5R2));
            } else if(reponse.get(0) == 3){
                ps.println(" > " + resources.getString(R.string.question5R3));
            } else if(reponse.get(0) == 4){
                ps.println(" > " + resources.getString(R.string.question5R4));
            }
            ps.println();

            //6eme question
            ps.println("Answer " + resources.getString(R.string.question6));
            reponse = getQuizAnswer(6);
            ps.println(" > " + reponse.get(0));
            ps.println();

            //7eme question
            ps.println("Answer " + resources.getString(R.string.question7));
            reponse = getQuizAnswer(7);
            ps.println(" > " + reponse.get(0));
            ps.println();

            //8eme question
            ps.println("Answer " + resources.getString(R.string.question8));
            reponse = getQuizAnswer(8);
            for(int i = 0; i < reponse.size(); i++){
                n = reponse.get(i);
                if (n == 1){
                    ps.println(" > " + resources.getString(R.string.question8R1));
                }
                if (n == 2){
                    ps.println(" > " + resources.getString(R.string.question8R2));
                }
                if (n == 3){
                    ps.println(" > " + resources.getString(R.string.question8R3));
                }
                if (n == 4){
                    ps.println(" > " + resources.getString(R.string.question8R4));
                }
            }
            ps.println();

            //9eme question
            ps.println("Answer " + resources.getString(R.string.question9));
            reponse = getQuizAnswer(9);
            if(reponse.get(0) == 1){
                ps.println(" > " + resources.getString(R.string.question9R1));
            } else if(reponse.get(0) == 2){
                ps.println(" > " + resources.getString(R.string.question9R2));
            } else if(reponse.get(0) == 3){
                ps.println(" > " + resources.getString(R.string.question9R3));
            } else if(reponse.get(0) == 4){
                ps.println(" > " + resources.getString(R.string.question9R4));
            }
            ps.println();

        } catch (IOException e) {
            e.printStackTrace();
            Log.e("FILE_ERROR", "Error writing quiz data to file", e);
        }
    }
}
