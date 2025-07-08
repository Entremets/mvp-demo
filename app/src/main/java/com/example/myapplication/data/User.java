package com.example.myapplication.data;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("userId") // 映射JSON字段名
    private String userId;
    private String id;
    private String title;

    private String body;

    // 必须有无参构造方法（Gson反序列化需要）
    public User() {}

}