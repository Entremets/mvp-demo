package com.example.myapplication.ble;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.ble.BleDevice;

import java.util.ArrayList;
import java.util.List;

/**
 * BLE设备列表适配器
 */
public class BleDeviceAdapter extends BaseAdapter {
    private Context context;
    private List<BleDevice> devices;

    public BleDeviceAdapter(Context context) {
        this.context = context;
        this.devices = new ArrayList<>();
    }

    public void setDevices(List<BleDevice> devices) {
        this.devices = devices;
    }

    @Override
    public int getCount() {
        return devices.size();
    }

    @Override
    public Object getItem(int position) {
        return devices.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_ble_device, parent, false);
            holder = new ViewHolder();
            holder.nameTextView = convertView.findViewById(R.id.device_name);
            holder.addressTextView = convertView.findViewById(R.id.device_address);
            holder.rssiTextView = convertView.findViewById(R.id.device_rssi);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        BleDevice device = devices.get(position);
//        holder.nameTextView.setText("名称: " + device.getDeviceName());
        holder.addressTextView.setText("地址: " + device.getDeviceAddress());
        holder.rssiTextView.setText("信号强度: " + device.getRssi() + " dBm");

        return convertView;
    }

    static class ViewHolder {
        TextView nameTextView;
        TextView addressTextView;
        TextView rssiTextView;
    }
}
