package com.example.myapplication.di;

import com.example.myapplication.login.LoginActivity;
import com.google.android.datatransport.runtime.dagger.Component;

@Component(modules = LoginModule.class)
public interface LoginComponent {
    void inject(LoginActivity activity);
}
