package com.example.myapplication.login;



import android.util.Log;

import com.example.myapplication.data.ApiService;
import com.example.myapplication.data.User;
import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LoginModel implements LoginContract.Model {
    private final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/") // 基础 URL
            .addConverterFactory(GsonConverterFactory.create()) // JSON 解析
            .build();

    private final ApiService apiService = retrofit.create(ApiService.class);


    @Override
    public void login(String username, String password, OnLoginListener listener) {
        apiService.getUser().enqueue(new retrofit2.Callback<User>() {
            @Override
            public void onResponse(retrofit2.Call<User> call, retrofit2.Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                    Gson gson = new Gson();
                    String json = gson.toJson(user);
                    Log.d("Login", "User JSON: " + json);
                    listener.onSuccess(user);
                } else {
                    listener.onFailure("请求失败: " + response.code());
                }
            }

            @Override
            public void onFailure(retrofit2.Call<User> call, Throwable throwable) {
                listener.onFailure("网络错误: ");
            }

        });
    }


}