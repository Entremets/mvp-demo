package com.example.myapplication.login;



import android.util.Log;

import com.example.myapplication.data.ApiService;
import com.example.myapplication.data.User;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LoginModel implements LoginContract.Model {
    private final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000") // 基础 URL
            .addConverterFactory(GsonConverterFactory.create()) // JSON 解析
            .build();

    private final ApiService apiService = retrofit.create(ApiService.class);


    @Override
    public void login(String username, String password, OnLoginListener listener) {
        apiService.getUser().enqueue(new retrofit2.Callback<List<User>>() {
            @Override
            public void onResponse(retrofit2.Call<List<User>> call, retrofit2.Response<List<User>> response) {
                if (response.isSuccessful()) {
                    List<User> user = response.body();
                    Gson gson = new Gson();
                    String json = gson.toJson(user);
                    Log.d("Login", "User JSON: " + json);
                    listener.onSuccess(user);
                } else {
                    listener.onFailure("请求失败: " + response.code());
                }
            }


            @Override
            public void onFailure(retrofit2.Call<List<User>> call, Throwable throwable) {
                Log.e("API", "请求失败: " + throwable.getMessage(), throwable);
                listener.onFailure("请求失败: " + throwable.getMessage());
            }

        });
    }


}