package com.example.myapplication.login;



import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.data.User;

import java.util.List;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    private EditText etUsername, etPassword;
    private View btnLogin;
    private ProgressBar progressBar;

    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        // 初始化View
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        progressBar = findViewById(R.id.progress_bar);

        presenter = new LoginPresenter(new LoginModel());
        presenter.attachView(this);

        btnLogin.setOnClickListener(v -> {
            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();
            Log.d("ButtonClicked", "点击"+username+password);
            presenter.login(username, password);
        });

    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onLoginSuccess(List<User> userList) {
        Log.d("onLoginSuccess", "登录成功");
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new UserAdapter(userList));
        UserAdapter adapter = new UserAdapter(userList);
        adapter.setOnItemClickListener(user -> {
            // 打印用户名称和邮箱
            Log.d("USER_CLICK", "Name: " + user.getName() + ", Email: " + user.getEmail());
        });




//        startActivity(new Intent(this, MainActivity.class));
//        finish();
    }

    @Override
    public void onLoginFailed(String error) {
        Log.d("onLoginFailed",  error);
//        startActivity(new Intent(this, MainActivity.class));
//        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}