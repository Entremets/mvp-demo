package com.example.myapplication.data;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("user_id") // 映射JSON字段名
    private String id;
    private String username;
    private String email;

    // 必须有无参构造方法（Gson反序列化需要）
    public User() {}

    public User(String id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    // Getter/Setter...
}