package com.example.aiqmind;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Random;

public class play extends AppCompatActivity {

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        findViewById(R.id.button_play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent[] intents = {
                        new Intent(play.this, question1.class),
                        new Intent(play.this, question2.class),
                        new Intent(play.this, question3.class)
                };

                // Generate a random index
                Random random = new Random();
                int randomIndex = random.nextInt(intents.length);

                // Start the random intent
                startActivity(intents[randomIndex]);
            }
        });

        mAuth= FirebaseAuth.getInstance();
        findViewById(R.id.button_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(v.getContext(), login.class);
                v.getContext().startActivity(intent);
            }

        });
    }
}