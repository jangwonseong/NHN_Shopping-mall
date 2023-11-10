package com.nhnacademy.shoppingmall.user.service.impl;

import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.exception.UserAlreadyExistsException;
import com.nhnacademy.shoppingmall.user.exception.UserNotFoundException;
import com.nhnacademy.shoppingmall.user.repository.UserRepository;
import com.nhnacademy.shoppingmall.user.service.UserService;

import java.time.LocalDateTime;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUser(String userId){
        //todo#13 회원조회
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public void saveUser(User user) {
        //todo#14 회원등록
        checkExistUser(user.getUserId());
        userRepository.save(user);
    }

    @Override
    public void updateUser(User user) {
        //todo#15 회원수정
        checkExistUser(user.getUserId());
        userRepository.update(user);
    }

    @Override
    public void deleteUser(String userId) {
        //todo#16 회원삭제
        checkExistUser(userId);
        userRepository.deleteByUserId(userId);
    }

    @Override
    public User doLogin(String userId, String userPassword) {
        //todo#17 로그인구현
        Optional<User> userOptional = userRepository.findByUserIdAndUserPassword(userId,userPassword);
        if(!userOptional.isPresent()){
            throw new UserNotFoundException(userId);
        }
        userRepository.updateLatestLoginAtByUserId(userId, LocalDateTime.now());
        return userOptional.get();
    }
    
    private void checkExistUser(String userId){
        if(userRepository.countByUserId(userId)>0){
            throw new UserAlreadyExistsException(userId);
        }
    }
}
