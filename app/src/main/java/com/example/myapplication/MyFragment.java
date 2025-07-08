package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class MyFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.my_fragment, container, false); // 第三个参数必须为false
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Fragment被创建时调用
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    // 在Activity的onCreate()方法返回后调用
    }

    @Override
    public void onStart() {
        super.onStart();
        // Fragment变为可见时调用
    }

    @Override
    public void onResume() {
        super.onResume();
        // Fragment即将与用户交互时调用
    }

    @Override
    public void onPause() {
        super.onPause();
        // Fragment不再与用户交互时调用
    }

    @Override
    public void onStop() {
        super.onStop();
        // Fragment不再可见时调用
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Fragment的视图层次结构被移除时调用
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Fragment被销毁时调用
    }

    @Override
    public void onDetach() {
        super.onDetach();
        // Fragment与Activity解除关联时调用
    }
}
