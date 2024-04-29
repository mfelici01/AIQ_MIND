package com.example.aiqmind.questions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aiqmind.QuizData;
import com.example.aiqmind.R;

import java.util.Calendar;
import java.util.List;

public class Question3 extends AppCompatActivity implements Question{

    TextView questionText;
    CalendarView answer;
    Button buttonNext;
    QuizData quizData;
    String selectedDate;
    TextView soluce;
    ImageView back;
    boolean alreadyAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question3);

        questionText = findViewById(R.id.dateQuestionText);
        answer = findViewById(R.id.calendarView);
        buttonNext = findViewById(R.id.dateNext);
        soluce = findViewById(R.id.dateAnswer);
        back = findViewById(R.id.backQuestion3);

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

        answer.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                selectedDate = dayOfMonth + "/" + (month+1) + "/" + year;
                buttonNext.setEnabled(true);
            }
        });

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (alreadyAnswer) {
                    quizData.nextCurrentQuestion();
                    quizData.nextActualPageQuestion();

                    colorChoice();
                    Intent intent = new Intent(view.getContext(), Question4.class);
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
            List<Integer> reponse = quizData.getQuizAnswer(3);
            Log.d("size", "Size reponse: " + reponse.size());
            selectedDate = reponse.get(0) + "/" + reponse.get(1) + "/" + reponse.get(2);

            alreadyAnswer = true;
            buttonNext.setText(getResources().getString(R.string.next));
            buttonNext.setEnabled(true);
            colorChoice();

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, reponse.get(2));
            calendar.set(Calendar.MONTH, reponse.get(1)-1);
            calendar.set(Calendar.DAY_OF_MONTH, reponse.get(0));

            long timestamp = calendar.getTimeInMillis();

            answer.setDate(timestamp);

            answer.setEnabled(false);
        }
    }

    public void updateDataAnswer(){
        if (quizData.getCurrentQuestion() == quizData.getActualPageQuestion()){
            String[] parts = selectedDate.split("/");
            int jour = Integer.parseInt(parts[0]);
            int mois = Integer.parseInt(parts[1]);
            int annee = Integer.parseInt(parts[2]);

            quizData.addQuizAnswer(jour);
            quizData.addQuizAnswer(mois);
            quizData.addQuizAnswer(annee);
        }
    }

    public void colorChoice() {
        soluce.setText(getResources().getString(R.string.question3R1));

        int green = ContextCompat.getColor(Question3.this, R.color.green);
        int red = ContextCompat.getColor(Question3.this, R.color.red);

        Log.d("date", "date : " + selectedDate);

        if (selectedDate.equals(getResources().getString(R.string.question3R1))){
            answer.setBackgroundColor(green);
        } else {
            answer.setBackgroundColor(red);
        }
        soluce.setBackgroundColor(green);
    }

    public void updateScore() {
        if (selectedDate.equals(getResources().getString(R.string.question3R1))) {
            Log.d("score", "Score avant : " + quizData.getScore());
            quizData.addScore();
            Log.d("score", "Score après : " + quizData.getScore());
        }
    }

    @Override
    public void onBackPressed(){
        quizData.backActualPageQuestion();
        Intent intent = new Intent(this, Question2.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("quizData", quizData);
        startActivity(intent);
        super.onBackPressed();
    }
}