package com.nhnacademy.shoppingmall.user.service.impl;

import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.dto.LoginResponse;
import com.nhnacademy.shoppingmall.user.dto.UserCreateRequest;
import com.nhnacademy.shoppingmall.user.exception.UserNotFoundException;
import com.nhnacademy.shoppingmall.user.repository.UserRepository;
import com.nhnacademy.shoppingmall.user.service.UserRegisterService;
import com.nhnacademy.shoppingmall.user.service.UserService;

import java.time.LocalDateTime;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserRegisterService userRegisterService;

    public UserServiceImpl(UserRepository userRepository, UserRegisterService userRegisterService) {
        this.userRepository = userRepository;
        this.userRegisterService = userRegisterService;
    }

    @Override
    public User getUser(String userId){
        //todo#4-1 회원조회
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다. (ID: " + userId + ")"));
    }

    @Override
    public void saveUser(User user) {
        //todo#4-2 회원등록 (UserRegisterService 사용)
        UserCreateRequest userCreateRequest = new UserCreateRequest(
                user.getUserId(),
                user.getUserName(),
                user.getUserPassword(),
                user.getUserBirth(),
                user.getUserPoint()
        );

        userRegisterService.registerUser(userCreateRequest);
    }

    @Override
    public void updateUser(User user) {
        //todo#4-3 회원수정
        if (userRepository.countByUserId(user.getUserId()) == 0) {
            throw new UserNotFoundException("사용자를 찾을 수 없습니다.");
        }
        userRepository.update(user);
    }

    @Override
    public void deleteUser(String userId) {
        //todo#4-4 회원삭제
        if (userRepository.countByUserId(userId) == 0) {
            throw new UserNotFoundException("사용자를 찾을 수 없습니다.");
        }
        userRepository.deleteByUserId(userId);
    }

    @Override
    public LoginResponse doLogin(String userId, String userPassword) {
        //todo#4-5 로그인 구현, userId, userPassword로 일치하는 회원 조회
        LoginResponse loginResponse = userRepository.findByUserIdAndUserPassword(userId, userPassword)
                .orElseThrow(() -> new UserNotFoundException("로그인 실패: 사용자를 찾을 수 없습니다"));

        userRepository.updateLatestLoginAtByUserId(userId, LocalDateTime.now());

        return loginResponse;
    }
}