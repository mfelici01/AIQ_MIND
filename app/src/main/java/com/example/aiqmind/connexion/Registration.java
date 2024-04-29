package com.example.aiqmind.connexion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.aiqmind.Play;
import com.example.aiqmind.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;
import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity {


    EditText username_rg;
    EditText password_rg;

    private static final String TAG = "registration";
    Button button_register;

    FirebaseAuth mAuth;

    FirebaseFirestore db;

    ProgressBar progressbar;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), Play.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mAuth= FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        progressbar=findViewById(R.id.progressBar);
        username_rg=findViewById(R.id.register_user);
        password_rg = findViewById(R.id.register_pass);
        button_register = (Button) findViewById(R.id.register_wus);

        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressbar.setVisibility(View.VISIBLE);
                String user_rg,pass_rg;
                user_rg= username_rg.getText().toString() + "@gmail.com";
                pass_rg=password_rg.getText().toString();

                if(TextUtils.isEmpty(user_rg)) {
                    Toast.makeText(Registration.this , "Enter Username", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(pass_rg)) {
                    Toast.makeText(Registration.this , "Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(user_rg, pass_rg)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressbar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {

                                    Log.d(TAG, "createUserWithEmail:success");
                                    Toast.makeText(Registration.this, "Account Created.",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(view.getContext(), Play.class);
                                    view.getContext().startActivity(intent);
                                    finish();

                                } else {
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(Registration.this, "Account Creating failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

                String score = "0";
                Map<String,Object> user = new HashMap<>();
                user.put("id",user_rg);
                user.put("score",score);
                user.put("date", FieldValue.serverTimestamp());
                db.collection("user")
                        .add(user)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(Registration.this,"Successful",Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull @NotNull Exception e) {

                                Toast.makeText(Registration.this, "Failed", Toast.LENGTH_SHORT).show();


                            }
                        });

            }
        });
    }
}