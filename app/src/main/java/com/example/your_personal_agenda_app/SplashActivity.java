package com.example.your_personal_agenda_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import static java.lang.Thread.sleep;

public class SplashActivity extends AppCompatActivity {

    private TextView tvCSQ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        tvCSQ = findViewById(R.id.tv_splash_activity);

        Typeface typeface = ResourcesCompat.getFont(this, R.font.pacifico);
        tvCSQ.setTypeface(typeface);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.myanim);
        tvCSQ.setAnimation(animation);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }).start();
    }
}