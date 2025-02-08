package com.nhnacademy.shoppingmall.user.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
public class UserCreateRequest {
    private String userId;
    private String userName;
    private String userPassword;
    private String userBirth;
    private int userPoint;

    public UserCreateRequest(String userId, String userName, String userPassword, String userBirth, int userPoint) {
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userBirth = userBirth;
        this.userPoint = userPoint;
    }
}
