package com.example.myapplication.login;

import com.example.myapplication.data.User;

// LoginPresenter.java
public class LoginPresenter implements LoginContract.Presenter {
    private LoginContract.View view;
    private final LoginContract.Model model;

    public LoginPresenter(LoginContract.Model model) {
        this.model = model;
    }

    @Override
    public void attachView(LoginContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public void login(String username, String password) {
        if (view != null) view.showLoading();

        model.login(username, password, new LoginContract.Model.OnLoginListener() {
            @Override
            public void onSuccess(User user) {
                if (view != null) {
                    view.hideLoading();
                    view.onLoginSuccess(user);
                }
            }


            @Override
            public void onFailure(String error) {
                if (view != null) {
                    view.hideLoading();
                    view.onLoginFailed(error);
                }
            }
        });
    }
}
