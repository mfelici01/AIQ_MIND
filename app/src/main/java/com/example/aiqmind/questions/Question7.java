package com.example.aiqmind.questions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aiqmind.QuizData;
import com.example.aiqmind.R;

import java.util.List;

public class Question7 extends AppCompatActivity implements Question{

    TextView questionText;
    EditText answer;
    TextView soluce;
    Button buttonNext;
    QuizData quizData;
    ImageView back;
    boolean alreadyAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question7);

        questionText = findViewById(R.id.inputQuestionText2);
        answer = findViewById(R.id.inputAnswer2);
        soluce = findViewById(R.id.inputSoluce2);
        buttonNext = findViewById(R.id.inputNext2);
        back = findViewById(R.id.backQuestion7);
        alreadyAnswer = false;
        buttonNext.setEnabled(false);

        Intent intent = getIntent();
        quizData = intent.getParcelableExtra("quizData");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        answer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkEditTextNotEmpty();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (alreadyAnswer) {
                    quizData.nextCurrentQuestion();
                    quizData.nextActualPageQuestion();

                    Intent intent = new Intent(view.getContext(), Question8.class);
                    intent.putExtra("quizData", quizData);
                    view.getContext().startActivity(intent);
                } else {
                    alreadyAnswer = true;
                    buttonNext.setText(getResources().getString(R.string.next));
                    colorChoice();

                    answer.setEnabled(false);
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
            List<Integer> reponse = quizData.getQuizAnswer(7);
            Log.d("size", "Size reponse: " + reponse.size());

            int intValue = reponse.get(0);
            String reponseString = Integer.toString(intValue);

            answer.setText(reponseString);

            answer.setEnabled(false);
            alreadyAnswer = true;
            buttonNext.setText(getResources().getString(R.string.next));
            colorChoice();
        }
    }

    public void updateDataAnswer(){
        String answerText = answer.getText().toString();
        int answerInt = Integer.parseInt(answerText);
        quizData.addQuizAnswer(answerInt);
    }

    public void checkEditTextNotEmpty() {
        if (!TextUtils.isEmpty(answer.getText().toString())) {
            buttonNext.setEnabled(true);
        } else {
            buttonNext.setEnabled(false);
        }
    }

    public void colorChoice() {
        int red = ContextCompat.getColor(this, R.color.red);
        int green = ContextCompat.getColor(this, R.color.green);

        if (TextUtils.isEmpty(answer.getText().toString())) {
            soluce.setText(getResources().getString(R.string.question7R1));
            soluce.setBackgroundColor(green);
        } else if (answer.getText().toString().equals(getResources().getString(R.string.question7R1))) {
            answer.setBackgroundColor(green);
        } else {
            soluce.setText(getResources().getString(R.string.question7R1));
            soluce.setBackgroundColor(green);
            answer.setBackgroundColor(red);
        }
    }

    public void updateScore() {
        if (!TextUtils.isEmpty(answer.getText().toString()) &&
                answer.getText().toString().equals(getResources().getString(R.string.question7R1))) {
            Log.d("score", "Score avant : " + quizData.getScore());
            quizData.addScore();
            Log.d("score", "Score après : " + quizData.getScore());
        }
    }

    @Override
    public void onBackPressed(){
        quizData.backActualPageQuestion();
        Intent intent = new Intent(this, Question6.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("quizData", quizData);
        startActivity(intent);
        super.onBackPressed();
    }
}