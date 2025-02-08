package com.nhnacademy.shoppingmall.user.service;

import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.dto.LoginResponse;

public interface UserService {
    User getUser(String userId);
    void saveUser(User user);
    void updateUser(User user);
    void deleteUser(String userId);
    LoginResponse doLogin(String userId, String userPassword); // LoginResponse 반환
}
