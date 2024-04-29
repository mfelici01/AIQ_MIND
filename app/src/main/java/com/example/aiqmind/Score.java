package com.example.aiqmind;

import static android.os.VibrationEffect.DEFAULT_AMPLITUDE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;


public class Score extends AppCompatActivity {

    FirebaseAuth auth;

    static final String TAG = "Score Activity";

    FirebaseUser user;

    String username;

    FirebaseFirestore db;

    int score;

    int nbQuestion;

    QuizData quizData;

    ProgressBar barprog;

    ProgressBar barbackgroud;

    TextView affscore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        auth= FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        Log.d(TAG, "firebase ok !");

        user=auth.getCurrentUser();

        Log.d(TAG, "firebase user get done !");

        assert user != null;
        username=user.getEmail();

        Log.d(TAG, "firebase user get mail done !");

        Intent intent = getIntent();
        quizData = intent.getParcelableExtra("quizData");

        Log.d(TAG, " quizData = intent.getParcelableExtra done !");

        assert quizData != null;
        score=quizData.getScore();
        nbQuestion=quizData.getCurrentQuestion();

        Log.d(TAG, " score=quizData.getScore();\n" +
                "        nbQuestion=quizData.getCurrentQuestion(); !");


        affscore=findViewById(R.id.number);
        if (score == 0 ){
            String text = 0+"nbQuestion";
            affscore.setText(text);
        }
        else {
            affscore.setText(String.valueOf(score + "/"+ nbQuestion));
        }
        barprog = findViewById(R.id.stats_progressbar);
        barbackgroud =findViewById(R.id.background_progressbar);
        barprog.setProgress(score);

        Log.d(TAG, " score=quizData.getScore();\n" +
                "        nbQuestion=quizData.getCurrentQuestion(); !");

        Button  home = findViewById(R.id.Home);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Score.this, Play.class);
                startActivity(intent);
            }
        });

        Button save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = user.getEmail();

                String[] parts = email.split("@");
                quizData.writeQuizDataToFile(Score.this, parts[0]);
                toast(getResources().getString(R.string.saveDone));
            }
        });

        // Initialize Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference usersCollection = db.collection("user");

        Log.d(TAG, " user !"+ user.getEmail());
        Log.d(TAG, " score !"+ score);


        // Perform a query to find documents where the "id" field matches the user's email
        Query query = usersCollection;
        Log.d(TAG, " query ok  !"+ user.getEmail());
        // Execute the query
        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                            // Get the document ID
                            String documentId = documentSnapshot.getId();
                            if (documentSnapshot.getString("id").equals(user.getEmail())&& documentSnapshot.getString("score").equals("0")) {
                                // Update the score field in the document
                                usersCollection.document(documentId)
                                        .update("score", String.valueOf(score))
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d(TAG, "Score updated successfully!");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.e(TAG, "Failed to update score: ", e);
                                            }
                                        });
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Error querying documents: ", e);
                    }
                });
        vibrate(200);
    }

    public void toast(String msg) {
        Toast.makeText(this, msg,Toast.LENGTH_SHORT).show();
    }

    public void vibrate(int duration) {
        duration = Math.max(duration, 1);
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null) {
            vibrator.vibrate(VibrationEffect.createOneShot(duration, DEFAULT_AMPLITUDE));
        }
    }

    }
