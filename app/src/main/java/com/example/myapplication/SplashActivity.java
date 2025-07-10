package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // 判断是否为首次启动
        SharedPreferences preferences = getSharedPreferences("app_prefs", MODE_PRIVATE);
        boolean isFirstLaunch = preferences.getBoolean("isFirstLaunch", true);

        handler = new Handler();
        handler.postDelayed(() -> {
            if (isFirstLaunch) {
                // 首次启动，跳转引导页
                startActivity(new Intent(this, GuideActivity.class));
                // 标记已启动
                preferences.edit().putBoolean("isFirstLaunch", false).apply();
            } else {
                // 非首次启动，跳转主界面
                startActivity(new Intent(this, MainActivity.class));
            }
            finish();
        }, 2000); // 延迟2秒
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 移除所有未处理的消息和回调
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }
}