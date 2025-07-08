package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.login.LoginActivity;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import androidx.fragment.app.Fragment;          // Fragment基类
import androidx.fragment.app.FragmentManager;   // Fragment管理器
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity{

    private View HomeBtn;

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        HomeBtn = findViewById(R.id.home_btn);
        Intent intent = new Intent();

        HomeBtn.setOnClickListener(v-> {
            intent.setClass(this, LoginActivity.class);
            startActivity(intent);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            int top = insets.getInsets(WindowInsetsCompat.Type.systemBars()).top;
            v.setPadding(v.getPaddingLeft(), top, v.getPaddingRight(), v.getPaddingBottom());
            return insets;
        });


        // 默认加载首页
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new HomeFragment())
                .commit();


        bottomNavigationView = findViewById(R.id.bottom_navigation);



        // 监听导航栏点击
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int id = item.getItemId();
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);

            if ((id == R.id.nav_home && currentFragment instanceof HomeFragment) ||
                    (id == R.id.nav_search && currentFragment instanceof SearchFragment) ||
                    (id == R.id.nav_profile && currentFragment instanceof ProfileFragment)) {
                return true;
            }

            if (id == R.id.nav_home) {
                selectedFragment = new HomeFragment();
            } else if (id == R.id.nav_search) {
                selectedFragment = new SearchFragment();
            } else if (id == R.id.nav_profile) {
                selectedFragment = new ProfileFragment();
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
//                        .addToBackStack(null)
                        .commit();
            }
            return true;
        });




        // 添加回退栈监听
//        getSupportFragmentManager().addOnBackStackChangedListener(() -> {
//            // 获取当前显示的Fragment
//            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
//            if (currentFragment != null) {
//                // 根据Fragment类型设置导航栏高亮
//                if (currentFragment instanceof HomeFragment) {
//                    bottomNavigationView.setSelectedItemId(R.id.nav_home);
//                } else if (currentFragment instanceof SearchFragment) {
//                    bottomNavigationView.setSelectedItemId(R.id.nav_search);
//                } else if (currentFragment instanceof ProfileFragment) {
//                    bottomNavigationView.setSelectedItemId(R.id.nav_profile);
//                }
//            }
//        });

    }

//    @Override
//    public void onBackPressed() {
//        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
//            getSupportFragmentManager().popBackStack();
//        } else {
//            super.onBackPressed();
//        }
//    }


}