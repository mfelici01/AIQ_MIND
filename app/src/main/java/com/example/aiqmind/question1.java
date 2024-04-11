package com.example.aiqmind;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.InputStream;

public class question1 extends AppCompatActivity {

    TextView Textquestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question1);

        Textquestion = findViewById(R.id.textView6);

        String firstQuestion = getResources().getString(R.string.question2);;
        Textquestion.setText(firstQuestion);

        Button buttonnext = findViewById(R.id.next);
        buttonnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Score.class);
                view.getContext().startActivity(intent);}
        });


    }
}