package com.nhnacademy.shoppingmall.user.dto;

import com.nhnacademy.shoppingmall.user.domain.User;

import java.time.LocalDateTime;

public class LoginResponse {
    private String userId;
    private String userName;
    private User.Auth userAuth;
    private LocalDateTime latestLoginAt;

    public LoginResponse(String userId, String userName, User.Auth userAuth, LocalDateTime latestLoginAt) {
        this.userId = userId;
        this.userName = userName;
        this.userAuth = userAuth;
        this.latestLoginAt = latestLoginAt;
    }

    public String getUserId() { return userId; }
    public String getUserName() { return userName; }
    public User.Auth getUserAuth() { return userAuth; }
    public LocalDateTime getLatestLoginAt() { return latestLoginAt; }
}
