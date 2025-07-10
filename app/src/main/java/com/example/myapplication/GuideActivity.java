package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

public class GuideActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private Button btnSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

//        viewPager = findViewById(R.id.viewPager);
//        btnSkip = findViewById(R.id.btnSkip);
//
//        // 设置ViewPager适配器（示例：使用FragmentPagerAdapter）
//        List<Fragment> fragments = new ArrayList<>();
//        fragments.add(GuideFragment.newInstance(R.drawable.guide1));
//        fragments.add(GuideFragment.newInstance(R.drawable.guide2));
//        fragments.add(GuideFragment.newInstance(R.drawable.guide3));
//
//        GuidePagerAdapter adapter = new GuidePagerAdapter(getSupportFragmentManager(), fragments);
//        viewPager.setAdapter(adapter);
//
//        // 跳过按钮点击事件
//        btnSkip.setOnClickListener(v -> {
//            startActivity(new Intent(this, MainActivity.class));
//            finish();
//        });
    }
}
