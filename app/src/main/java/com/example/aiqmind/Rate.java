package com.example.aiqmind;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.Toast;

public class Rate extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    float savedRating;

    private void saveRatingToXML(float rating) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat("user_rating", rating);
        editor.apply();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        sharedPreferences = getSharedPreferences("ratings", MODE_PRIVATE);
        savedRating = sharedPreferences.getFloat("user_rating", 0);

        RatingBar ratingBar = findViewById(R.id.ratingBar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                saveRatingToXML(rating);
                Toast.makeText(Rate.this , "Your rating has been registered , Thank you ! ", Toast.LENGTH_SHORT).show();
            }
        });
    }
}