package com.example.myapplication.ble;

import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;

import java.util.List;

/**
 * BLE服务模型类，包含服务及其特征值信息
 */
public class BleService {
    private BluetoothGattService service;
    private List<BluetoothGattCharacteristic> characteristics;

    public BleService(BluetoothGattService service, List<BluetoothGattCharacteristic> characteristics) {
        this.service = service;
        this.characteristics = characteristics;
    }

    public BluetoothGattService getService() {
        return service;
    }

    public String getServiceUuid() {
        return service.getUuid().toString();
    }

    public List<BluetoothGattCharacteristic> getCharacteristics() {
        return characteristics;
    }
}
