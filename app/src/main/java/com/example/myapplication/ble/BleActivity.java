package com.example.myapplication.ble;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.ble.BleDeviceAdapter;
import com.example.myapplication.ble.BleServiceAdapter;
import com.example.myapplication.ble.BleContract;
import com.example.myapplication.ble.BleDevice;
import com.example.myapplication.ble.BleService;
import com.example.myapplication.ble.BlePresenter;

import java.util.List;

/**
 * 视图层实现，负责UI展示和用户交互
 */
public class BleActivity extends AppCompatActivity implements BleContract.View {
    private static final int REQUEST_ENABLE_BT = 1;

    private ProgressBar progressBar;
    private TextView statusTextView;
    private Button scanButton;
    private Button disconnectButton;
    private ListView deviceListView;
    private ListView serviceListView;

    private BleDevice presenter;
    private BleDeviceAdapter deviceAdapter;
    private BleServiceAdapter serviceAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ble);

        // 初始化视图
        initViews();

        // 创建Presenter
        presenter = new BlePresenter(this, this);

        // 检查权限
        presenter.checkAndRequestPermissions();

        // 检查蓝牙状态
        if (!presenter.isBluetoothEnabled()) {
            presenter.enableBluetooth();
        }
    }

    // 初始化视图组件
    private void initViews() {
        progressBar = findViewById(R.id.progress_bar);
        statusTextView = findViewById(R.id.status_text);
        scanButton = findViewById(R.id.scan_button);
        disconnectButton = findViewById(R.id.disconnect_button);
        deviceListView = findViewById(R.id.device_list);
        serviceListView = findViewById(R.id.service_list);

        // 初始化适配器
        deviceAdapter = new BleDeviceAdapter(this);
        serviceAdapter = new BleServiceAdapter(this);

        deviceListView.setAdapter(deviceAdapter);
        serviceListView.setAdapter(serviceAdapter);

        // 扫描按钮点击事件
        scanButton.setOnClickListener(v -> {
            if (!presenter.isBluetoothEnabled()) {
                showError("请先开启蓝牙");
                presenter.enableBluetooth();
                return;
            }

            serviceAdapter.clear();
            presenter.startScan();
        });

        // 断开连接按钮点击事件
        disconnectButton.setOnClickListener(v -> presenter.disconnect());

        // 设备列表点击事件
        deviceListView.setOnItemClickListener((parent, view, position, id) -> {
            BleDevice bleDevice = (BleDevice) deviceAdapter.getItem(position);
            if (bleDevice != null) {
                presenter.stopScan();
                presenter.connectDevice(bleDevice.getDevice());
            }
        });
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateDeviceList(List<BleDevice> devices) {
        deviceAdapter.setDevices(devices);
        deviceAdapter.notifyDataSetChanged();
    }

    @Override
    public void showConnectionStatus(String status) {
        statusTextView.setText(status);
    }

    @Override
    public void showDeviceServices(List<BleService> services) {
        serviceAdapter.setServices(services);
        serviceAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 蓝牙开启结果回调
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == RESULT_OK) {
                showError("蓝牙已开启");
            } else {
                showError("请开启蓝牙以使用应用");
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // 权限请求结果处理
        presenter.checkAndRequestPermissions();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.onDestroy();
        }
    }
}
