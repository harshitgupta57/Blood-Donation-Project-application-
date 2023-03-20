package com.example.blooddonationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class splashsrceen extends AppCompatActivity {
    private ImageView logo1;
    private TextView text1,text2;
    Animation topanimation ,bottomanimation;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        logo1 = findViewById(R.id.logo1);
        text1 =findViewById(R.id.text1);
        text2 = findViewById(R.id.text2);


        topanimation = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomanimation = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        logo1.setAnimation(topanimation);
        text1.setAnimation(bottomanimation);
        text2.setAnimation(bottomanimation);


        int splash_screen = 4300;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(splashsrceen.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },splash_screen);

    }
}