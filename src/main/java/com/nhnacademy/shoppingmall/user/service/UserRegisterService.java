package com.nhnacademy.shoppingmall.user.service;

import com.nhnacademy.shoppingmall.user.dto.UserCreateRequest;

public interface UserRegisterService {
    void registerUser(UserCreateRequest userCreateRequest);
}
