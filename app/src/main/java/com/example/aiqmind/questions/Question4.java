package com.example.aiqmind.questions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.aiqmind.QuizData;
import com.example.aiqmind.R;

import java.util.List;

public class Question4 extends AppCompatActivity implements Question {

    TextView questionText;
    TextView answer1;
    TextView answer2;
    TextView answer3;
    TextView answer4;

    SeekBar seekBar;
    Button buttonNext;
    QuizData quizData;
    boolean alreadyAnswer;
    ImageView back;
    int max = 3;
    int step = 1;
    int currentProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question4);

        questionText = findViewById(R.id.seekBarreQuestionText);
        answer1 = findViewById(R.id.seekBarAnswer1);
        answer2 = findViewById(R.id.seekBarAnswer2);
        answer3 = findViewById(R.id.seekBarAnswer3);
        answer4 = findViewById(R.id.seekBarAnswer4);
        buttonNext = findViewById(R.id.seekBarNext);
        back = findViewById(R.id.backQuestion4);
        seekBar = findViewById(R.id.seekBar);
        alreadyAnswer = false;
        buttonNext.setEnabled(false);
        currentProgress = 0;

        Intent intent = getIntent();
        quizData = intent.getParcelableExtra("quizData");

        seekBar.setMax(max/step);
        seekBar.setProgress(1);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                currentProgress = progress;
                Log.d("progress", "progress : " + currentProgress);
                buttonNext.setEnabled(true);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (alreadyAnswer) {
                    quizData.nextCurrentQuestion();
                    quizData.nextActualPageQuestion();

                    Intent intent = new Intent(view.getContext(), Question5.class);
                    intent.putExtra("quizData", quizData);
                    view.getContext().startActivity(intent);
                } else {
                    alreadyAnswer = true;
                    buttonNext.setText(getResources().getString(R.string.next));
                    colorChoice();
                    seekBar.setEnabled(false);

                    updateDataAnswer();
                    updateScore();
                }
            }
        });
        updateAnswer();
    }

    public void updateAnswer(){
        Log.d("size", "actual page : " + quizData.getActualPageQuestion());
        Log.d("size", "current question : " + quizData.getCurrentQuestion());
        if(quizData.getActualPageQuestion() < quizData.getCurrentQuestion()){
            List<Integer> reponse = quizData.getQuizAnswer(4);
            Log.d("size", "Size reponse: " + reponse.size());

            currentProgress = reponse.get(0);
            seekBar.setProgress(currentProgress);

            alreadyAnswer = true;
            buttonNext.setText(getResources().getString(R.string.next));
            colorChoice();

            seekBar.setEnabled(false);
        }
    }

    public void updateDataAnswer(){
        if (quizData.getCurrentQuestion() == quizData.getActualPageQuestion()){
            quizData.addQuizAnswer(currentProgress);
        }
    }

    public void colorChoice() {
        answer1.setBackground(ContextCompat.getDrawable(Question4.this, R.drawable.green));

        answer2.setBackground(ContextCompat.getDrawable(Question4.this, R.drawable.red));
        answer4.setBackground(ContextCompat.getDrawable(Question4.this, R.drawable.red));
        answer3.setBackground(ContextCompat.getDrawable(Question4.this, R.drawable.red));
    }

    public void updateScore() {
        if (currentProgress == 0) {
            Log.d("score", "Score avant : " + quizData.getScore());
            quizData.addScore();
            Log.d("score", "Score après : " + quizData.getScore());
        }
    }

    @Override
    public void onBackPressed(){
        quizData.backActualPageQuestion();
        Intent intent = new Intent(this, Question3.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("quizData", quizData);
        startActivity(intent);
        super.onBackPressed();
    }


}