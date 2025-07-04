package com.example.myapplication.login;

public class CredentialsValidator {

    /**
            * 验证用户名和密码
     * @param username 用户名
     * @param password 密码
     * @return 错误消息（null 表示验证通过）
            */
    public String validate(String username, String password) {
        // 规则1：用户名非空
        if (username == null || username.trim().isEmpty()) {
            return "用户名不能为空";
        }

        // 规则2：密码长度至少6位
        if (password == null || password.length() < 6) {
            return "密码至少6位";
        }

        // 规则3：用户名不能包含特殊字符
        if (!username.matches("[a-zA-Z0-9_]+")) {
            return "用户名只能包含字母、数字或下划线";
        }

        // 所有规则通过
        return null;
    }
}
