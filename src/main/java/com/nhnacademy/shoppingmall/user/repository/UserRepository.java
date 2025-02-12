package com.nhnacademy.shoppingmall.user.repository;

import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.dto.LoginResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<LoginResponse> findByUserIdAndUserPassword(String userId, String userPassword);
    Optional<User> findById(String userId);
    int save(User user);
    int deleteByUserId(String userId);
    int update(User user);
    int updateLatestLoginAtByUserId(String userId, LocalDateTime latestLoginAt);
    int countByUserId(String userId);
    List<User> findAll();
}
