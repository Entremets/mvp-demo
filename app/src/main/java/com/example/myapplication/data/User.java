package com.example.myapplication.data;


public class User {
    private int id;
    private String name;
    private String email;
    private String role;
    // 必须有无参构造方法（Gson反序列化需要）
    public User() {}

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }
}