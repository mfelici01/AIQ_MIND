package com.example.aiqmind;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


import java.util.*;

public class Score extends AppCompatActivity {

    FirebaseAuth auth;

    static final String TAG = "Read Data Activity";

    FirebaseUser user;

    String username;

    FirebaseFirestore db;

    String score;

    ProgressBar barprog;

    TextView affscore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        auth= FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        user=auth.getCurrentUser();

        assert user != null;
        username=user.getEmail();


        getScoreForID(username);



    }


    private void getScoreForID(String targetID) {
        db.collection("user")
                .whereEqualTo("id",targetID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()){

                            Toast.makeText(Score.this,"Successful",Toast.LENGTH_LONG).show();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String score = document.getString("score");
                                if (score != null) {
                                    updateProgressBar(Integer.parseInt(score));
                                }
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }



                        }else {

                            Toast.makeText(Score.this,"Failed",Toast.LENGTH_LONG).show();

                        }

                    }
                });

    }

    private void updateProgressBar(int scoreValue) {

        affscore=findViewById(R.id.number);
        if (score == null){
        affscore.setText("0/10");
        }
        else {
            affscore.setText(score);
        }
        barprog = findViewById(R.id.stats_progressbar);
        barprog.setProgress(scoreValue);
    }
}