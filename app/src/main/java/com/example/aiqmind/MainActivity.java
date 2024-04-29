package com.example.aiqmind;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aiqmind.connexion.Log;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button1 = (Button) findViewById(R.id.play_button);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Log.class);
                view.getContext().startActivity(intent);
            }
        });

        String url = "https://blogs.nvidia.com/";




        Button button_S_W = (Button) findViewById(R.id.topics_button);

        button_S_W.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });


        ImageView rate_us = findViewById(R.id.imageView3);

        rate_us.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Rate.class);
                view.getContext().startActivity(intent);
            }

        });

        TextView rate = findViewById(R.id.textView3);

        rate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Rate.class);
                view.getContext().startActivity(intent);
            }

        });

        ImageView share_us = findViewById(R.id.imageView2);

        share_us.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view){
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Check out this app!");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Hey, I found this cool app. You should check it out: https://play.google.com/store/apps/details?id=" + getPackageName());

                startActivity(Intent.createChooser(shareIntent, "Share via"));
            }
        });


        TextView share = findViewById(R.id.share);

        share.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view){
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Check out this app!");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Hey, I found this cool app. You should check it out: https://play.google.com/store/apps/details?id=" + getPackageName());

                startActivity(Intent.createChooser(shareIntent, "Share via"));
            }
        });

        verifyStoragePermissions(this);verifyStoragePermissions(this);
    }

    public static void verifyStoragePermissions(Activity activity) {
        // Vérifie si nous avons les droits d’écriture
        int permission = ActivityCompat.checkSelfPermission(activity,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // Aie, il faut les demander àl’utilisateur
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Déconnexion de Firebase Auth
        FirebaseAuth.getInstance().signOut();
    }
}