package com.example.aiqmind.connexion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aiqmind.R;
import com.example.aiqmind.Rate;


public class Log extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        Button buttonlog = (Button) findViewById(R.id.connexion);
        buttonlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Login.class);
                view.getContext().startActivity(intent);}
        });

        Button buttonregister = (Button) findViewById(R.id.registration);
        buttonregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Registration.class);
                view.getContext().startActivity(intent);}
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
    }
    }