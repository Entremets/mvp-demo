package com.example.myapplication.ble;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.myapplication.ble.BleContract;
import com.example.myapplication.ble.BleDevice;
import com.example.myapplication.ble.BleService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.bluetooth.BluetoothGatt.GATT_SUCCESS;
import static android.content.Context.BLUETOOTH_SERVICE;

/**
 * BlePresenter实现类，处理蓝牙相关业务逻辑
 */
public class BlePresenter implements BleContract.Presenter {
    private static final int REQUEST_ENABLE_BT = 1;
    private static final int REQUEST_PERMISSIONS = 2;
    private static final long SCAN_PERIOD = 10000; // 扫描时长10秒

    private BleContract.View view;
    private Context context;
    private BluetoothManager bluetoothManager;
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothLeScanner bluetoothLeScanner;
    private BluetoothGatt bluetoothGatt;
    private Handler handler;
    private List<BleDevice> deviceList;
    private boolean isScanning;

    public BlePresenter(BleContract.View view, Context context) {
        this.view = view;
        this.context = context;
        this.handler = new Handler();
        this.deviceList = new ArrayList<>();
        initBluetooth();
    }

    // 初始化蓝牙管理器
    private void initBluetooth() {
        bluetoothManager = (BluetoothManager) context.getSystemService(BLUETOOTH_SERVICE);
        if (bluetoothManager != null) {
            bluetoothAdapter = bluetoothManager.getAdapter();
        }
    }

    @Override
    public void checkAndRequestPermissions() {
        List<String> permissions = new ArrayList<>();

        // 检查蓝牙权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.BLUETOOTH_SCAN)
                    != PackageManager.PERMISSION_GRANTED) {
                permissions.add(android.Manifest.permission.BLUETOOTH_SCAN);
            }
            if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.BLUETOOTH_CONNECT)
                    != PackageManager.PERMISSION_GRANTED) {
                permissions.add(android.Manifest.permission.BLUETOOTH_CONNECT);
            }
        }

        // 检查位置权限（Android 12以下需要）
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S &&
                ContextCompat.checkSelfPermission(context, ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            permissions.add(ACCESS_FINE_LOCATION);
        }

        if (!permissions.isEmpty()) {
            ActivityCompat.requestPermissions((Activity) context,
                    permissions.toArray(new String[0]), REQUEST_PERMISSIONS);
        }
    }

    @Override
    public boolean isBluetoothEnabled() {
        return bluetoothAdapter != null && bluetoothAdapter.isEnabled();
    }

    @Override
    public void enableBluetooth() {
        if (bluetoothAdapter != null && !bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            ((Activity) context).startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
    }

    @Override
    public void startScan() {
        if (isScanning || bluetoothAdapter == null) {
            return;
        }

        view.showLoading();
        deviceList.clear();
        view.updateDeviceList(deviceList);

        bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();

        // 检查扫描权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S &&
                ContextCompat.checkSelfPermission(context, android.Manifest.permission.BLUETOOTH_SCAN)
                        != PackageManager.PERMISSION_GRANTED) {
            view.hideLoading();
            view.showError("缺少蓝牙扫描权限");
            return;
        }

        // 开始扫描
        bluetoothLeScanner.startScan(scanCallback);
        isScanning = true;

        // 定时停止扫描
        handler.postDelayed(() -> {
            stopScan();
            view.hideLoading();
            view.showError("扫描已结束");
        }, SCAN_PERIOD);
    }

    @Override
    public void stopScan() {
        if (isScanning && bluetoothLeScanner != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S &&
                    ContextCompat.checkSelfPermission(context, android.Manifest.permission.BLUETOOTH_SCAN)
                            != PackageManager.PERMISSION_GRANTED) {
                return;
            }

            bluetoothLeScanner.stopScan(scanCallback);
            isScanning = false;
            view.hideLoading();
        }
    }

    @Override
    public void connectDevice(BluetoothDevice device) {
        if (bluetoothAdapter == null || device == null) {
            view.showError("无法连接设备");
            return;
        }

        // 检查连接权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S &&
                ContextCompat.checkSelfPermission(context, android.Manifest.permission.BLUETOOTH_CONNECT)
                        != PackageManager.PERMISSION_GRANTED) {
            view.showError("缺少蓝牙连接权限");
            return;
        }

        view.showLoading();
        view.showConnectionStatus("正在连接到 " + device.getAddress());

        // 连接设备
        bluetoothGatt = device.connectGatt(context, false, gattCallback);
    }

    @Override
    public void disconnect() {
        if (bluetoothGatt != null) {
            bluetoothGatt.disconnect();
            bluetoothGatt.close();
            bluetoothGatt = null;
            view.showConnectionStatus("已断开连接");
        }
    }

    @Override
    public void onDestroy() {
        stopScan();
        disconnect();
        view = null;
    }

    // BLE扫描回调
    private final ScanCallback scanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);
            if (result != null && result.getDevice() != null) {
                addDeviceToList(result.getDevice(), result.getRssi());
            }
        }

        @Override
        public void onBatchScanResults(List<ScanResult> results) {
            super.onBatchScanResults(results);
            for (ScanResult result : results) {
                if (result != null && result.getDevice() != null) {
                    addDeviceToList(result.getDevice(), result.getRssi());
                }
            }
        }

        @Override
        public void onScanFailed(int errorCode) {
            super.onScanFailed(errorCode);
            view.hideLoading();
            view.showError("扫描失败，错误码: " + errorCode);
            isScanning = false;
        }
    };

    // 添加设备到列表，避免重复
    private void addDeviceToList(BluetoothDevice device, int rssi) {
        boolean isExist = false;
        for (BleDevice bleDevice : deviceList) {
            if (bleDevice.getDeviceAddress().equals(device.getAddress())) {
                bleDevice = new BleDevice(device, rssi);
                isExist = true;
                break;
            }
        }

        if (!isExist) {
            deviceList.add(new BleDevice(device, rssi));
            view.updateDeviceList(deviceList);
        }
    }

    // GATT回调
    private final BluetoothGattCallback gattCallback = new BluetoothGattCallback() {
        // 连接状态变化
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            if (newState == android.bluetooth.BluetoothProfile.STATE_CONNECTED) {
                // 连接成功，开始发现服务
                ((Activity) context).runOnUiThread(() -> {
                    view.showConnectionStatus("连接成功，正在发现服务...");
                });

                if (ActivityCompat.checkSelfPermission(context,
                        android.Manifest.permission.BLUETOOTH_CONNECT)
                        != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                gatt.discoverServices();
            } else if (newState == android.bluetooth.BluetoothProfile.STATE_DISCONNECTED) {
                // 连接断开
                ((Activity) context).runOnUiThread(() -> {
                    view.hideLoading();
                    view.showConnectionStatus("连接已断开");
                });
            }
        }

        // 发现服务回调
        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if (status == GATT_SUCCESS) {
                // 获取所有服务
                List<BluetoothGattService> services = gatt.getServices();
                List<BleService> bleServices = new ArrayList<>();

                for (BluetoothGattService service : services) {
                    List<BluetoothGattCharacteristic> characteristics = service.getCharacteristics();
                    bleServices.add(new BleService(service, characteristics));
                }

                // 在UI线程更新
                ((Activity) context).runOnUiThread(() -> {
                    view.hideLoading();
                    view.showConnectionStatus("发现 " + bleServices.size() + " 个服务");
                    view.showDeviceServices(bleServices);
                });
            } else {
                ((Activity) context).runOnUiThread(() -> {
                    view.hideLoading();
                    view.showError("发现服务失败，错误码: " + status);
                });
            }
        }
    };
}

