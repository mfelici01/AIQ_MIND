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
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {


    FirebaseAuth mAuth;
    TextInputEditText username;

    private static final String TAG = "sign in";
    EditText password;

    ProgressBar progressbar;

    FirebaseFirestore db;

    Button button_connexion;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null)
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
        setContentView(R.layout.activity_login);
        mAuth= FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        progressbar=findViewById(R.id.progressBar);
        username=findViewById(R.id.username);
        password = findViewById(R.id.loginTextPassword);
        button_connexion = (Button) findViewById(R.id.connexion);
        button_connexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressbar.setVisibility(View.VISIBLE);
                String user,pass;
                user= username.getText().toString()+ "@gmail.com";
                pass=password.getText().toString();

                if(TextUtils.isEmpty(user)) {
                    Toast.makeText(Login.this , "Enter Username", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(pass)) {
                    Toast.makeText(Login.this , "Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }


                mAuth.signInWithEmailAndPassword(user, pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressbar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "signIn:success");
                                    Toast.makeText(Login.this, "Authentication Success.",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(view.getContext(), Play.class);
                                    view.getContext().startActivity(intent);
                                    finish();
                                } else {
                                    Log.w(TAG, "signIn:failure", task.getException());
                                    Toast.makeText(Login.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                String score = "0";
                Map<String,Object> client = new HashMap<>();
                client.put("id",user);
                client.put("score",score);
                client.put("date", FieldValue.serverTimestamp());
                db.collection("user")
                        .add(client)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(Login.this,"Successful",Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull @NotNull Exception e) {

                                Toast.makeText(Login.this, "Failed", Toast.LENGTH_SHORT).show();


                            }
                        });
            }
        });
    }
}