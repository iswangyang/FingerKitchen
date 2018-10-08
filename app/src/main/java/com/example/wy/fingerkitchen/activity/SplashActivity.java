package com.example.wy.fingerkitchen.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.wy.fingerkitchen.R;

/**
 * author: wangyang
 * date:   2018/10/7 21:15
 * description:
 * 指尖厨房---启动Activity
 */
public class SplashActivity extends AppCompatActivity {

    private static final long SPLASH_TIME_GAP = 3 * 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        jumpToMainActivity();
    }

    private void jumpToMainActivity() {
        //延时跳转主Activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setClass(SplashActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }, SPLASH_TIME_GAP);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
