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

public class Question8 extends AppCompatActivity implements Question {

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
        setContentView(R.layout.activity_question8);

        questionText = findViewById(R.id.checkBoxQuestionText2);
        answer1 = findViewById(R.id.checkBoxAnswer1_2);
        answer2 = findViewById(R.id.checkBoxAnswer2_2);
        answer3 = findViewById(R.id.checkBoxAnswer3_2);
        answer4 = findViewById(R.id.checkBoxAnswer4_2);
        buttonNext = findViewById(R.id.checkBoxNext2);
        back = findViewById(R.id.backQuestion8);
        alreadyAnswer = false;
        buttonNext.setEnabled(false);

        Intent intent = getIntent();
        quizData = intent.getParcelableExtra("quizData");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Play.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

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
                    quizData.nextCurrentQuestion();
                    quizData.nextActualPageQuestion();

                    Intent intent = new Intent(view.getContext(), Question9.class);
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

    public void updateAnswer(){
        Log.d("size", "actual page : " + quizData.getActualPageQuestion());
        Log.d("size", "current question : " + quizData.getCurrentQuestion());
        if(quizData.getActualPageQuestion() < quizData.getCurrentQuestion()){
            List<Integer> reponse = quizData.getQuizAnswer(8);
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

    public void checkAtLeastOneChecked() {
        if (answer1.isChecked() || answer2.isChecked() || answer3.isChecked() || answer4.isChecked()) {
            buttonNext.setEnabled(true);
        } else {
            buttonNext.setEnabled(false);
        }
    }

    public void colorChoice() {
        int red = ContextCompat.getColor(this, R.color.red);
        int green = ContextCompat.getColor(this, R.color.green);

        CompoundButtonCompat.setButtonTintList(answer1, ColorStateList.valueOf(green));
        CompoundButtonCompat.setButtonTintList(answer4, ColorStateList.valueOf(green));

        CompoundButtonCompat.setButtonTintList(answer3, ColorStateList.valueOf(red));
        CompoundButtonCompat.setButtonTintList(answer2, ColorStateList.valueOf(red));

    }

    public void updateScore() {
        if (answer1.isChecked() && answer4.isChecked()) {
            Log.d("score", "Score avant : " + quizData.getScore());
            quizData.addScore();
            Log.d("score", "Score après : " + quizData.getScore());
        }
    }

    @Override
    public void onBackPressed(){
        quizData.backActualPageQuestion();
        Intent intent = new Intent(this, Question7.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("quizData", quizData);
        startActivity(intent);
        super.onBackPressed();
    }
}