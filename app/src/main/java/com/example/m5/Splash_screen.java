package com.example.m5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

/************************************************************************************
 *
 *  The Splash Screen activity shows the user a Splash screen with an image and text.
 *  After some time, the user is taken to the main activity screen
 *
 *************************************************************************************/

public class Splash_screen extends AppCompatActivity {

    Handler handler;
    final int TIME_LIMIT = 8000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(Splash_screen.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        },TIME_LIMIT);
    }
}

/*********************************** End of Splash Screen ****************************************/