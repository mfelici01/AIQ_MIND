package com.example.aiqmind;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class log extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        Button buttonlog = (Button) findViewById(R.id.connexion);
        buttonlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), login.class);
                view.getContext().startActivity(intent);}
        });

        Button buttonregister = (Button) findViewById(R.id.registration);
        buttonregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), registration.class);
                view.getContext().startActivity(intent);}
        });
    }
    }