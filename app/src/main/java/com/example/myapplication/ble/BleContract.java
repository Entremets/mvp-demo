package com.example.myapplication.ble;

import android.bluetooth.BluetoothDevice;

import com.example.myapplication.ble.BleDevice;
import com.example.myapplication.ble.BleService;

import java.util.List;

/**
 * MVP契约类，定义View和Presenter的接口
 */
public interface BleContract {

    interface View {
        // 显示加载中
        void showLoading();

        // 隐藏加载中
        void hideLoading();

        // 显示错误信息
        void showError(String message);

        // 更新设备列表
        void updateDeviceList(List<BleDevice> devices);

        // 显示连接状态
        void showConnectionStatus(String status);

        // 显示设备服务
        void showDeviceServices(List<BleService> services);
    }

    interface Presenter {
        // 检查并请求蓝牙权限
        void checkAndRequestPermissions();

        // 检查蓝牙是否开启
        boolean isBluetoothEnabled();

        // 开启蓝牙
        void enableBluetooth();

        // 开始扫描设备
        void startScan();

        // 停止扫描设备
        void stopScan();

        // 连接设备
        void connectDevice(BluetoothDevice device);

        // 断开连接
        void disconnect();

        // 释放资源
        void onDestroy();
    }
}
