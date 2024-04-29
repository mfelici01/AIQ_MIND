package com.example.aiqmind;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.aiqmind.history.Item;
import com.example.aiqmind.history.ItemsAdapter;
import com.example.aiqmind.questions.Question1;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Play extends AppCompatActivity {

    FirebaseAuth mAuth;

    TextView useraff;

    static final String TAG = "historyplayActivity";

    private RecyclerView recyclerView;
    private ItemsAdapter adapter;
    private FirebaseFirestore firestore;
    private CollectionReference itemsCollection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        mAuth= FirebaseAuth.getInstance();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ItemsAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);


        firestore = FirebaseFirestore.getInstance();
        itemsCollection = firestore.collection("user");


        String email =mAuth.getCurrentUser().getEmail();

        String[] parts = email.split("@");

        Log.d(TAG, "Email: " + email);

        itemsCollection.addSnapshotListener((snapshot, exception) -> {
            if (exception != null) {
                Log.e(TAG, "Error fetching items", exception);
                return;
            }

            List<Item> itemsList = new ArrayList<>();
            for (DocumentSnapshot document : snapshot.getDocuments()) {
                Log.d(TAG, "Nombre de documents renvoyés : " + snapshot.size());
                String id = document.getString("id");
                String score = document.getString("score");
                Date date = document.getDate("date");
                Log.e(TAG, "id item 1: "+ id);
                Log.e(TAG, "id item 2 : "+email);
                if ((id != null) && (id.equals(email))) {
                    Item item = new Item(date, id, score);
                    itemsList.add(item);
                }
            }
            adapter = new ItemsAdapter(itemsList);
            recyclerView.setAdapter(adapter);
        });



        useraff = findViewById(R.id.textuser);
        useraff.setText("Hello " + parts[0].toUpperCase() + " Ready to Play!");
        findViewById(R.id.button_play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Question1.class);

                QuizData quizData = new QuizData();
                intent.putExtra("quizData", quizData);
                v.getContext().startActivity(intent);
            }
        });


        findViewById(R.id.button_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                v.getContext().startActivity(intent);
            }

        });
    }
}