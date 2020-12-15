package com.chennupatibalu.filedrive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        imageView = findViewById(R.id.imageView);

        int splash_screen_time_out = 2000;
        new Handler().postDelayed(() -> {

            startActivity(new Intent(SplashActivity.this,LoginActivity.class));
            finish();
        }, splash_screen_time_out);
    }
}