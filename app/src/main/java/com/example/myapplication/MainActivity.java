package com.example.myapplication;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    int SPLASH_SCREEN=5000;
    Animation topanim;
    Animation rightanim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        topanim =AnimationUtils.loadAnimation(this,R.anim.top_anim);
        rightanim =AnimationUtils.loadAnimation(this,R.anim.right_anim);
         ImageView img =findViewById(R.id.logo1);
         TextView txt2=findViewById(R.id.text1);
        img.setAnimation(rightanim);
        txt2.setAnimation(topanim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, Login_page.class);
                Pair<View, String> logoTextPair = Pair.create(txt2,"logo_text");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, logoTextPair);
                startActivity(intent, options.toBundle());

            }
        }, SPLASH_SCREEN);



    }
}