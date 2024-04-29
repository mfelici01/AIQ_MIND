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
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.aiqmind.QuizData;
import com.example.aiqmind.R;
import com.example.aiqmind.Score;

import java.util.List;

public class Question9 extends AppCompatActivity implements Question {

    TextView questionText;
    RadioButton answer1;
    RadioButton answer2;
    RadioButton answer3;
    RadioButton answer4;

    RadioGroup group;
    ImageView back;
    Button buttonNext;
    QuizData quizData;
    boolean alreadyAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question9);

        questionText = findViewById(R.id.radioQuestionText2);
        answer1 = findViewById(R.id.radioAnswer1_2);
        answer2 = findViewById(R.id.radioAnswer2_2);
        answer3 = findViewById(R.id.radioAnswer3_2);
        answer4 = findViewById(R.id.radioAnswer4_2);
        buttonNext = findViewById(R.id.radioNext2);
        back = findViewById(R.id.backQuestion9);
        group = findViewById(R.id.groupRadio2);
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

        RadioGroup.OnCheckedChangeListener radioListener = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                checkAtLeastOneChecked();
            }
        };

        group.setOnCheckedChangeListener(radioListener);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (alreadyAnswer) {
                    quizData.nextCurrentQuestion();
                    quizData.nextActualPageQuestion();

                    Intent intent = new Intent(view.getContext(), Score.class);
                    intent.putExtra("quizData", quizData);
                    view.getContext().startActivity(intent);
                } else {
                    alreadyAnswer = true;
                    buttonNext.setText(getResources().getString(R.string.next));
                    colorChoice();

                    for (int i = 0; i < group.getChildCount(); i++) {
                        group.getChildAt(i).setEnabled(false);
                    }

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
            List<Integer> reponse = quizData.getQuizAnswer(9);
            Log.d("size", "Size reponse: " + reponse.size());

            RadioButton radioButton = (RadioButton) group.getChildAt(reponse.get(0)-1);
            radioButton.setChecked(true);

            alreadyAnswer = true;
            buttonNext.setText(getResources().getString(R.string.next));
            colorChoice();

            for (int i = 0; i < group.getChildCount(); i++) {
                group.getChildAt(i).setEnabled(false);
            }
        }
    }

    public void updateDataAnswer(){
        if (quizData.getCurrentQuestion() == quizData.getActualPageQuestion()){
            //Récupérer le bouton radio sélectionné
            View radioButton = group.findViewById(group.getCheckedRadioButtonId());

            //recuperer sa position dans le groupe
            int position = group.indexOfChild(radioButton) + 1;

            quizData.addQuizAnswer(position);
        }
    }

    public void checkAtLeastOneChecked() {
        if (group.getCheckedRadioButtonId() != -1) {
            buttonNext.setEnabled(true);
        } else {
            buttonNext.setEnabled(false);
        }
    }

    public void colorChoice() {
        int red = ContextCompat.getColor(this, R.color.red);
        int green = ContextCompat.getColor(this, R.color.green);

        CompoundButtonCompat.setButtonTintList(answer1, ColorStateList.valueOf(red));
        CompoundButtonCompat.setButtonTintList(answer3, ColorStateList.valueOf(red));
        CompoundButtonCompat.setButtonTintList(answer4, ColorStateList.valueOf(red));

        CompoundButtonCompat.setButtonTintList(answer2, ColorStateList.valueOf(green));
    }

    public void updateScore() {
        if (answer2.isChecked()) {
            Log.d("score", "Score avant : " + quizData.getScore());
            quizData.addScore();
            Log.d("score", "Score après : " + quizData.getScore());
        }
    }

    @Override
    public void onBackPressed(){
        quizData.backActualPageQuestion();
        Intent intent = new Intent(this, Question8.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("quizData", quizData);
        startActivity(intent);
        super.onBackPressed();
    }
}