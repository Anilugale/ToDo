package com.marocks.todo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        findViewById(R.id.splash).postDelayed(new Runnable() {
            @Override
            public void run() {
              startActivity(new Intent(Splash.this,MainActivity.class));
                finish();
            }
        },1000);


    }

    @Override
    public void onBackPressed() {

    }
}
