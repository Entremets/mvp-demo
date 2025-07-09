package com.example.myapplication.login;

import com.example.myapplication.data.User;

import java.util.List;

// LoginContract.java
public interface LoginContract {
    interface View {
        void showLoading();
        void hideLoading();
        void onLoginSuccess(List<User> user);
        void onLoginFailed(String error);
    }

    interface Presenter {
        void attachView(View view);
        void detachView();
        void login(String username, String password);
    }

    interface Model {
        interface OnLoginListener {
            void onSuccess(List<User> user);
            void onFailure(String error);
        }

        void login(String username, String password, OnLoginListener callback);
    }

}
