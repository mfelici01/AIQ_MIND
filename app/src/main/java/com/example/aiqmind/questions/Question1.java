package com.example.aiqmind.questions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.widget.CompoundButtonCompat;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aiqmind.QuizData;
import com.example.aiqmind.R;
import com.example.aiqmind.Play;

import java.util.List;

public class Question1 extends AppCompatActivity implements Question{

    TextView questionText;
    CheckBox answer1;
    CheckBox answer2;
    CheckBox answer3;
    CheckBox answer4;
    Button buttonNext;
    QuizData quizData;
    boolean alreadyAnswer;

    ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question1);

        //initialisation des widgets
        questionText = findViewById(R.id.checkBoxQuestionText);
        answer1 = findViewById(R.id.checkBoxAnswer1);
        answer2 = findViewById(R.id.checkBoxAnswer2);
        answer3 = findViewById(R.id.checkBoxAnswer3);
        answer4 = findViewById(R.id.checkBoxAnswer4);
        buttonNext = findViewById(R.id.checkBoxNext);
        back = findViewById(R.id.backQuestion1);

        //desactivation du button answer tant qu'il n'a pas repondu à la question
        alreadyAnswer = false;
        buttonNext.setEnabled(false);

        //recuperation de la classe Parcelable
        Intent intent = getIntent();
        quizData = intent.getParcelableExtra("quizData");

        //button retour en arriere qui renvoie sur l'activitée play
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //verifier si au moin un des checkBoxs est cocher
        CompoundButton.OnCheckedChangeListener checkBoxListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkAtLeastOneChecked();
            }
        };

        answer1.setOnCheckedChangeListener(checkBoxListener);
        answer2.setOnCheckedChangeListener(checkBoxListener);
        answer3.setOnCheckedChangeListener(checkBoxListener);
        answer4.setOnCheckedChangeListener(checkBoxListener);


        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (alreadyAnswer){
                    //passer à la prochaine question tout en faisant des updates de quizData
                    quizData.nextCurrentQuestion();
                    quizData.nextActualPageQuestion();

                    Intent intent = new Intent(view.getContext(), Question2.class);
                    intent.putExtra("quizData", quizData);
                    view.getContext().startActivity(intent);
                } else {
                    //Enregistrer la reponse du joueur et desactiver les checkBoxs
                    alreadyAnswer = true;

                    //le boutton answer devient next
                    buttonNext.setText(getResources().getString(R.string.next));

                    //colorier en vert les bonnes réponses et en rouge les mauvaises
                    colorChoice();

                    answer1.setEnabled(false);
                    answer2.setEnabled(false);
                    answer3.setEnabled(false);
                    answer4.setEnabled(false);

                    //enregistrement de la reponse du joueur dans la classe
                    updateDataAnswer();
                    //update le score du joueur
                    updateScore();
                }
            }
        });

        updateAnswer();
    }

    //Fonction qui enregistre les reponses du joueur à la question
    public void updateDataAnswer(){
        if (quizData.getCurrentQuestion() == quizData.getActualPageQuestion()){
            if (answer1.isChecked()){
                quizData.addQuizAnswer(1);
            }
            if (answer2.isChecked()){
                quizData.addQuizAnswer(2);
            }
            if (answer3.isChecked()){
                quizData.addQuizAnswer(3);
            }
            if (answer4.isChecked()){
                quizData.addQuizAnswer(4);
            }
        }
    }

    //Fonction qui verifie si l'utilisateur àa déjà repondu à une question et est revenu en arriere
    //Cette fonction desactive donc tout option de repondre à nouveau
    public void updateAnswer(){
        Log.d("size", "actual page : " + quizData.getActualPageQuestion());
        Log.d("size", "current question : " + quizData.getCurrentQuestion());
        if(quizData.getActualPageQuestion() < quizData.getCurrentQuestion()){
            List<Integer> reponse = quizData.getQuizAnswer(1);
            Log.d("size", "Size reponse: " + reponse.size());
            int n;
            for(int i = 0; i < reponse.size(); i++){
                n = reponse.get(i);
                if (n == 1){
                    answer1.setChecked(true);
                }
                if (n == 2){
                    answer2.setChecked(true);
                }
                if (n == 3){
                    answer3.setChecked(true);
                }
                if (n == 4){
                    answer4.setChecked(true);
                }
            }

            alreadyAnswer = true;
            buttonNext.setText(getResources().getString(R.string.next));
            colorChoice();

            answer1.setEnabled(false);
            answer2.setEnabled(false);
            answer3.setEnabled(false);
            answer4.setEnabled(false);
        }
    }

    //Fonction qui check si au moin un des checkBoxs est coché et activer le boutton answer
    public void checkAtLeastOneChecked() {
        if (answer1.isChecked() || answer2.isChecked() || answer3.isChecked() || answer4.isChecked()) {
            buttonNext.setEnabled(true);
        } else {
            buttonNext.setEnabled(false);
        }
    }

    //Fonction qui met en couleur les bonnes et mauvaises réponses
    public void colorChoice() {
        int red = ContextCompat.getColor(this, R.color.red);
        int green = ContextCompat.getColor(this, R.color.green);

        CompoundButtonCompat.setButtonTintList(answer1, ColorStateList.valueOf(red));
        CompoundButtonCompat.setButtonTintList(answer3, ColorStateList.valueOf(red));

        CompoundButtonCompat.setButtonTintList(answer2, ColorStateList.valueOf(green));
        CompoundButtonCompat.setButtonTintList(answer4, ColorStateList.valueOf(green));
    }


    //Fonction qui update le score apres que le joueur ai repondu
    public void updateScore() {
        if (answer2.isChecked() && answer4.isChecked()) {
            Log.d("score", "Score avant : " + quizData.getScore());
            quizData.addScore();
            Log.d("score", "Score après : " + quizData.getScore());
        }
    }

    //Fonction qui s'occupe du retour en arriere
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, Play.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        super.onBackPressed();
    }
}