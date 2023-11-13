package com.example.your_personal_agenda_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RatingBar;


public class InfoActivity extends AppCompatActivity {

    public static final String STELE_FILE_NAME = "steleSharedPref";
    public static final String STELE_KEY = "stele_key";
    private RatingBar ratingBar;
    private SharedPreferences preferences;
    private float stele = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        initComponents();
    }

    private void initComponents() {
        ratingBar = findViewById(R.id.rb_info_activity_feedback);
        preferences = getSharedPreferences(STELE_FILE_NAME,MODE_PRIVATE);
        stele = preferences.getFloat(STELE_KEY,0);
        ratingBar.setRating(stele);
        ratingBar.setOnRatingBarChangeListener(steleChangeEventListener());
    }

    private RatingBar.OnRatingBarChangeListener steleChangeEventListener() {
        return new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    stele = ratingBar.getRating();
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putFloat(STELE_KEY, stele);
                    editor.apply();
            }
        };
    }

}