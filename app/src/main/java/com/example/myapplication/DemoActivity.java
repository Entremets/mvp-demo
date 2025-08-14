package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class DemoActivity extends AppCompatActivity implements View.OnClickListener {


    private TextView tvCheckSettings;
    private TextView tvAccessibility;
    private TextView tvBrightnessSound;
    private TextView tvTimeLanguage;
    private TextView tvSystem;
    private TextView tvAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_demo);
        // 初始化左侧导航项
        tvCheckSettings = findViewById(R.id.tv_check_settings);
        tvAccessibility = findViewById(R.id.tv_accessibility);
        tvBrightnessSound = findViewById(R.id.tv_brightness_sound);
        tvTimeLanguage = findViewById(R.id.tv_time_language);
        tvSystem = findViewById(R.id.tv_system);
        tvAbout = findViewById(R.id.tv_about);

        // 设置点击事件
        tvCheckSettings.setOnClickListener(this);
        tvAccessibility.setOnClickListener(this);
        tvBrightnessSound.setOnClickListener(this);
        tvTimeLanguage.setOnClickListener(this);
        tvSystem.setOnClickListener(this);
        tvAbout.setOnClickListener(this);

        // 返回箭头点击
        findViewById(R.id.iv_back).setOnClickListener(v -> finish());

        // 默认显示“检查设置”页面
        showFragment(new DemoFragment());
    }

    @Override
    public void onClick(View v) {
        // 重置所有导航项的文字颜色
        resetNavTextColor();

        // 根据点击项切换 Fragment
        Fragment fragment = null;
        if (v.getId() == R.id.tv_check_settings) {
            tvCheckSettings.setTextColor(getResources().getColor(android.R.color.white));
            fragment = new DemoFragment();
        } else if (v.getId() == R.id.tv_accessibility) {
            tvAccessibility.setTextColor(getResources().getColor(android.R.color.white));
            // fragment = new AccessibilityFragment(); // 需创建对应 Fragment
        } else if (v.getId() == R.id.tv_brightness_sound) {
            tvBrightnessSound.setTextColor(getResources().getColor(android.R.color.white));
            // fragment = new BrightnessSoundFragment(); // 需创建对应 Fragment
        } else if (v.getId() == R.id.tv_time_language) {
            tvTimeLanguage.setTextColor(getResources().getColor(android.R.color.white));
            // fragment = new TimeLanguageFragment(); // 需创建对应 Fragment
        } else if (v.getId() == R.id.tv_system) {
            tvSystem.setTextColor(getResources().getColor(android.R.color.white));
            // fragment = new SystemSettingsFragment(); // 需创建对应 Fragment
        } else if (v.getId() == R.id.tv_about) {
            tvAbout.setTextColor(getResources().getColor(android.R.color.white));
            // fragment = new AboutFragment(); // 需创建对应 Fragment
        }

        if (fragment != null) {
            showFragment(fragment);
        }
    }

    // 显示 Fragment
    private void showFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.content_container, fragment);
        transaction.commit();
    }

    // 重置导航项文字颜色为灰色
    private void resetNavTextColor() {
        tvCheckSettings.setTextColor(getResources().getColor(R.color.gray));
        tvAccessibility.setTextColor(getResources().getColor(R.color.gray));
        tvBrightnessSound.setTextColor(getResources().getColor(R.color.gray));
        tvTimeLanguage.setTextColor(getResources().getColor(R.color.gray));
        tvSystem.setTextColor(getResources().getColor(R.color.gray));
        tvAbout.setTextColor(getResources().getColor(R.color.gray));
    }
}