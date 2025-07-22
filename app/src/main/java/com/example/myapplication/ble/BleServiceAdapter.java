package com.example.myapplication.ble;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import com.example.myapplication.R;
import com.example.myapplication.ble.BleService;

import java.util.ArrayList;
import java.util.List;

/**
 * BLE服务列表适配器，使用ExpandableList展示服务和特征值
 */
public class BleServiceAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<BleService> services;

    public BleServiceAdapter(Context context) {
        this.context = context;
        this.services = new ArrayList<>();
    }

    public void setServices(List<BleService> services) {
        this.services = services;
        notifyDataSetChanged();
    }

    public void clear() {
        services.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return services.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return services.get(groupPosition).getCharacteristics().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return services.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return services.get(groupPosition).getCharacteristics().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder groupHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_service_group, parent, false);
            groupHolder = new GroupViewHolder();
            groupHolder.serviceUuidTextView = convertView.findViewById(R.id.service_uuid);
            convertView.setTag(groupHolder);
        } else {
            groupHolder = (GroupViewHolder) convertView.getTag();
        }

        BleService service = services.get(groupPosition);
        groupHolder.serviceUuidTextView.setText("服务 UUID: " + service.getServiceUuid());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_characteristic_child, parent, false);
            childHolder = new ChildViewHolder();
            childHolder.characteristicUuidTextView = convertView.findViewById(R.id.characteristic_uuid);
            childHolder.propertiesTextView = convertView.findViewById(R.id.characteristic_properties);
            convertView.setTag(childHolder);
        } else {
            childHolder = (ChildViewHolder) convertView.getTag();
        }

        // 获取特征值
        var characteristic = services.get(groupPosition).getCharacteristics().get(childPosition);

        // 显示特征值UUID
        childHolder.characteristicUuidTextView.setText("特征值 UUID: " + characteristic.getUuid().toString());

        // 显示特征值属性
        String properties = getCharacteristicProperties(characteristic.getProperties());
        childHolder.propertiesTextView.setText("属性: " + properties);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    // 解析特征值属性
    private String getCharacteristicProperties(int properties) {
        StringBuilder sb = new StringBuilder();

        if ((properties & android.bluetooth.BluetoothGattCharacteristic.PROPERTY_READ) != 0) {
            sb.append("读, ");
        }
        if ((properties & android.bluetooth.BluetoothGattCharacteristic.PROPERTY_WRITE) != 0) {
            sb.append("写, ");
        }
        if ((properties & android.bluetooth.BluetoothGattCharacteristic.PROPERTY_NOTIFY) != 0) {
            sb.append("通知, ");
        }
        if ((properties & android.bluetooth.BluetoothGattCharacteristic.PROPERTY_INDICATE) != 0) {
            sb.append("指示, ");
        }

        if (sb.length() > 0) {
            sb.setLength(sb.length() - 2);
        } else {
            sb.append("无");
        }

        return sb.toString();
    }

    static class GroupViewHolder {
        TextView serviceUuidTextView;
    }

    static class ChildViewHolder {
        TextView characteristicUuidTextView;
        TextView propertiesTextView;
    }
}

