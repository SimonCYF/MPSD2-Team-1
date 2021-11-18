package com.example.mpsd2.userpackage;

import android.content.Intent;
import android.os.Bundle;
import android.text.NoCopySpan;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.example.mpsd2.R;
import com.example.mpsd2.swipe.SwipeMain;

import android.os.Handler;

public class Animation extends AppCompatActivity {
    ImageView logo, appName, splashImg;
    LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        splashImg = findViewById(R.id.intro);
        lottieAnimationView = findViewById(R.id.lottie);

        splashImg.animate().translationY(-2000).setDuration(1000).setStartDelay(2500);
        lottieAnimationView.animate().translationY(-2000).setDuration(1000).setStartDelay(3600);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Animation.this, SwipeMain.class);
                startActivity(intent);
            }
        }, 4400);
    }
}
