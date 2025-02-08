package com.nhnacademy.shoppingmall.user.service.impl;

import com.nhnacademy.shoppingmall.user.dto.UserCreateRequest;

import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.UserRepository;
import com.nhnacademy.shoppingmall.user.service.UserRegisterService;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
public class UserRegisterServiceImpl implements UserRegisterService {
    private final UserRepository userRepository;

    public UserRegisterServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void registerUser(UserCreateRequest userCreateRequest) {
        User user = new User(
                userCreateRequest.getUserId(),
                userCreateRequest.getUserName(),
                userCreateRequest.getUserPassword(),
                userCreateRequest.getUserBirth(),
                User.Auth.ROLE_USER, // 기본 권한 설정
                1_000_000, // 회원 가입 시 100만 포인트 지급
                LocalDateTime.now(),
                null
        );

        userRepository.save(user);

        log.info("회원 가입 성공 - userId: {}", user.getUserId());
    }
}
