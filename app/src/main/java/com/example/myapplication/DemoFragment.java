package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import androidx.fragment.app.Fragment;

public class DemoFragment extends Fragment {

    private Switch switchBootCheck;
    private Switch switchDataMonitor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_demo, container, false);

        // 初始化 Switch
        switchBootCheck = view.findViewById(R.id.switch_boot_check);
        switchDataMonitor = view.findViewById(R.id.switch_data_monitor);

        // 开关状态监听（示例）
        switchBootCheck.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // 处理“开机直接进入检查”开关逻辑
        });

        switchDataMonitor.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // 处理“数据监测开关”逻辑
        });

        // 可点击项监听（示例）
        view.findViewById(R.id.layout_device_config).setOnClickListener(v -> {
            // 跳转到“配置检测设备”页面
        });

        view.findViewById(R.id.layout_interval_time).setOnClickListener(v -> {
            // 跳转到“间隔测量时间”设置页面
        });

        return view;
    }
}