package com.example.myapplication.login;

import com.example.myapplication.data.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import okhttp3.*;
import java.io.IOException;

public class LoginModel implements LoginContract.Model {
    private final OkHttpClient httpClient;
    private final CredentialsValidator validator;
    private final Gson gson = new Gson();
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    public LoginModel(OkHttpClient httpClient, CredentialsValidator validator) {
        this.httpClient = httpClient;
        this.validator = validator;
    }

    @Override
    public void login(String username, String password, OnLoginListener listener) {
        // 1. 验证输入
        String validationError = validator.validate(username, password);
        if (validationError != null) {
            listener.onFailure(validationError);
            return;
        }

        // 2. 构建JSON请求体
        JsonObject jsonBody = new JsonObject();
        jsonBody.addProperty("username", username);
        jsonBody.addProperty("password", password);
        RequestBody body = RequestBody.create(jsonBody.toString(), JSON);

        // 3. 创建请求
        Request request = new Request.Builder()
                .url("https://api.example.com/login")
                .post(body)
                .build();

        // 4. 异步发送请求
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.onFailure("网络连接失败: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    listener.onFailure("登录失败: " + response.code());
                    return;
                }

                try {
                    // 5. 解析响应数据
                    String responseData = response.body().string();
                    User user = gson.fromJson(responseData, User.class);
                    listener.onSuccess(user);
                } catch (Exception e) {
                    listener.onFailure("数据解析错误: " + e.getMessage());
                }
            }
        });
    }
}