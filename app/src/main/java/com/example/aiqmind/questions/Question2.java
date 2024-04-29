package com.example.aiqmind.questions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aiqmind.QuizData;
import com.example.aiqmind.R;

import java.util.List;

public class Question2 extends AppCompatActivity implements Question {

    TextView questionText;
    ImageView answer1;
    ImageView answer2;
    ImageView answer3;
    ImageView answer4;
    Button buttonNext;
    QuizData quizData;
    boolean alreadyAnswer;
    boolean goNextPage;
    ImageView lastClick;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question2);

        //initialisation des widgets
        questionText = findViewById(R.id.imageQuestionText);
        answer1 = findViewById(R.id.imageAnswer1);
        answer2 = findViewById(R.id.imageAnswer2);
        answer3 = findViewById(R.id.imageAnswer3);
        answer4 = findViewById(R.id.imageAnswer4);
        buttonNext = findViewById(R.id.imageNext);
        back = findViewById(R.id.backQuestion2);

        //desactivation du button answer tant qu'il n'a pas repondu à la question
        alreadyAnswer = false;
        buttonNext.setEnabled(false);
        lastClick = null;
        goNextPage = false;

        //recuperation de la classe Parcelable
        Intent intent = getIntent();
        quizData = intent.getParcelableExtra("quizData");

        ImageView.OnClickListener imageClickListener = new ImageView.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView imageView = (ImageView) view;

                Log.d("score", "Score : " + quizData.getScore());

                //Enlever le background
                if(lastClick != null && !goNextPage){
                    lastClick.setBackground(null);
                }

                if(!goNextPage){
                    lastClick = imageView;
                    imageView.setBackground(ContextCompat.getDrawable(Question2.this, R.drawable.square_selected));
                    checkOneClick();
                }
            }
        };

        answer1.setOnClickListener(imageClickListener);
        answer2.setOnClickListener(imageClickListener);
        answer3.setOnClickListener(imageClickListener);
        answer4.setOnClickListener(imageClickListener);

        //button retour en arriere qui renvoie sur la question precedente
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (alreadyAnswer) {
                    quizData.nextCurrentQuestion();
                    quizData.nextActualPageQuestion();

                    colorChoice();
                    goNextPage = true;
                    Intent intent = new Intent(view.getContext(), Question3.class);
                    intent.putExtra("quizData", quizData);
                    view.getContext().startActivity(intent);
                } else {
                    alreadyAnswer = true;
                    buttonNext.setText(getResources().getString(R.string.next));
                    colorChoice();

                    answer1.setEnabled(false);
                    answer2.setEnabled(false);
                    answer3.setEnabled(false);
                    answer4.setEnabled(false);

                    updateDataAnswer();
                    updateScore();
                }
            }
        });

        updateAnswer();
    }

    //Fonction qui verifie si l'utilisateur àa déjà repondu à une question et est revenu en arriere
    //Cette fonction desactive donc tout option de repondre à nouveau
    public void updateAnswer(){
        Log.d("size", "actual page : " + quizData.getActualPageQuestion());
        Log.d("size", "current question : " + quizData.getCurrentQuestion());
        if(quizData.getActualPageQuestion() < quizData.getCurrentQuestion()){
            List<Integer> reponse = quizData.getQuizAnswer(2);
            Log.d("size", "Size reponse: " + reponse.size());
            int n = reponse.get(0);

            if (n == 1){
                lastClick = answer1;
            } else if (n == 2){
                lastClick = answer2;
            } else if (n == 3){
                lastClick = answer3;
            } else if (n == 4){
                lastClick = answer4;
            }

            alreadyAnswer = true;
            buttonNext.setText(getResources().getString(R.string.next));
            colorChoice();
            buttonNext.setEnabled(true);

            answer1.setEnabled(false);
            answer2.setEnabled(false);
            answer3.setEnabled(false);
            answer4.setEnabled(false);
        }
    }

    //Fonction qui enregistre les reponses du joueur à la question
    public void updateDataAnswer(){
        if (quizData.getCurrentQuestion() == quizData.getActualPageQuestion()){
            if (lastClick.getId() == R.id.imageAnswer1){
                quizData.addQuizAnswer(1);
            }
            if (lastClick.getId() == R.id.imageAnswer2){
                quizData.addQuizAnswer(2);
            }
            if (lastClick.getId() == R.id.imageAnswer3){
                quizData.addQuizAnswer(3);
            }
            if (lastClick.getId() == R.id.imageAnswer4){
                quizData.addQuizAnswer(4);
            }
        }
    }

    //Fonction qui check si au moin un des checkBoxs est coché et activer le boutton answer
    public void checkOneClick() {
        if (lastClick != null) {
            buttonNext.setEnabled(true);
        } else {
            buttonNext.setEnabled(false);
        }
    }

    //Fonction qui met en couleur les bonnes et mauvaises réponses
    public void colorChoice() {
        lastClick.setBackground(null);

        answer1.setBackground(ContextCompat.getDrawable(Question2.this, R.drawable.red));
        answer2.setBackground(ContextCompat.getDrawable(Question2.this, R.drawable.red));
        answer4.setBackground(ContextCompat.getDrawable(Question2.this, R.drawable.red));

        answer3.setBackground(ContextCompat.getDrawable(Question2.this, R.drawable.green));
    }

    //Fonction qui update le score apres que le joueur ai repondu
    public void updateScore() {
        if (lastClick.getId() == R.id.imageAnswer3) {
            Log.d("score", "Score avant : " + quizData.getScore());
            quizData.addScore();
            Log.d("score", "Score après : " + quizData.getScore());
        }
    }

    //Fonction qui s'occupe du retour en arriere
    @Override
    public void onBackPressed(){
        quizData.backActualPageQuestion();
        Intent intent = new Intent(this, Question1.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("quizData", quizData);
        startActivity(intent);
        super.onBackPressed();
    }
}