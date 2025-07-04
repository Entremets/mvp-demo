package com.example.myapplication.di;

import com.example.myapplication.login.CredentialsValidator;
import com.example.myapplication.login.LoginContract;
import com.example.myapplication.login.LoginModel;
import com.google.android.datatransport.runtime.dagger.Module;
import com.google.android.datatransport.runtime.dagger.Provides;

@Module
public class LoginModule {
    @Provides
    LoginContract.Model provideLoginModel(AuthApiService apiService, CredentialsValidator validator) {
        return new LoginModel(apiService, validator);
    }

    @Provides
    CredentialsValidator provideValidator() {
        return new CredentialsValidator();
    }
}
