package com.example.myapplication.ble;

import android.Manifest;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;

/**
 * BLE设备模型类，存储设备信息
 */
public class BleDevice {
    private BluetoothDevice device;
    private int rssi;

    public BleDevice(BluetoothDevice device, int rssi) {
        this.device = device;
        this.rssi = rssi;
    }

    public BluetoothDevice getDevice() {
        return device;
    }

    public String getDeviceName(Context context) {

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return "权限不足，无法获取设备名称";
        }
        return device.getName() != null ? device.getName() : "未知设备";
    }

    public String getDeviceAddress() {
        return device.getAddress();
    }

    public int getRssi() {
        return rssi;
    }
}

