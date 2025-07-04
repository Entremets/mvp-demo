package com.example.myapplication.login;

import com.example.myapplication.data.User;

// LoginContract.java
public interface LoginContract {
    interface View {
        void showLoading();
        void hideLoading();
        void onLoginSuccess(User user);
        void onLoginFailed(String error);
        void showInputError(InputField field, String message);
    }

    interface Presenter {
        void attachView(View view);
        void detachView();
        void login(String username, String password);
    }

    interface Model {
        interface OnLoginListener {
            void onSuccess(User user);
            void onFailure(String error);
        }
        void login(String username, String password, OnLoginListener listener);
    }

    enum InputField { USERNAME, PASSWORD }
}
