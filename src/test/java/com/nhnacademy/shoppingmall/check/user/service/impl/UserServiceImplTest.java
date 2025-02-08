package com.nhnacademy.shoppingmall.check.user.service.impl;

import com.nhnacademy.shoppingmall.user.dto.LoginResponse;
import com.nhnacademy.shoppingmall.user.dto.UserCreateRequest;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.exception.UserAlreadyExistsException;
import com.nhnacademy.shoppingmall.user.exception.UserNotFoundException;
import com.nhnacademy.shoppingmall.user.repository.UserRepository;
import com.nhnacademy.shoppingmall.user.service.UserRegisterService;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

//todo#4-6 Test Code가 통과하도록 UserServiceImpl을 구현합니다.

@Slf4j
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    UserRepository userRepository = Mockito.mock(UserRepository.class);
    UserRegisterService userRegisterService = Mockito.mock(UserRegisterService.class); // Mock 객체 생성
    UserService userService;
    User testUser;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userRepository, userRegisterService); // UserRegisterService 주입
        testUser = new User("nhnacademy-test-user", "nhn아카데미", "nhnacademy-test-password", "19900505", User.Auth.ROLE_USER, 100_0000, LocalDateTime.now(), LocalDateTime.now());
    }

    @Test
    @DisplayName("getUser")
    void getUser() {
        Mockito.when(userRepository.findById(anyString())).thenReturn(Optional.of(testUser));
        userService.getUser(testUser.getUserId());
        Mockito.verify(userRepository, Mockito.times(1)).findById(anyString());
    }

    @Test
    @DisplayName("getUser - user not found -> throw exception")
    void getUser_not_found() {
        Mockito.when(userRepository.findById(anyString())).thenReturn(Optional.empty());
        Assertions.assertThrows(UserNotFoundException.class, () -> userService.getUser(testUser.getUserId())); // 예외 발생 확인
    }

    @Test
    @DisplayName("save user - already exist user")
    void saveUser_exist() {
        Mockito.when(userRepository.countByUserId(anyString())).thenReturn(1);
        User testUser = new User("existingUser", "name", "password", "birth", User.Auth.ROLE_USER, 1000, LocalDateTime.now(), null);
        Assertions.assertThrows(UserAlreadyExistsException.class, () -> userService.saveUser(testUser));
        Mockito.verify(userRepository, Mockito.times(1)).countByUserId(anyString());
    }

    @Test
    @DisplayName("save user")
    void saveUser() {
        Mockito.when(userRepository.countByUserId(anyString())).thenReturn(0);
        Mockito.doNothing().when(userRegisterService).registerUser(any(UserCreateRequest.class));

        User testUser = new User("newUser", "name", "password", "birth", User.Auth.ROLE_USER, 1000, LocalDateTime.now(), null);
        Assertions.assertDoesNotThrow(() -> userService.saveUser(testUser));

        Mockito.verify(userRepository, Mockito.times(1)).countByUserId(anyString());
        Mockito.verify(userRegisterService, Mockito.times(1)).registerUser(any(UserCreateRequest.class));
    }


    @Test
    @DisplayName("update user")
    void updateUser() {
        Mockito.when(userRepository.countByUserId(anyString())).thenReturn(1);
        Mockito.when(userRepository.update(any())).thenReturn(1); // ArgumentMatchers.any()로 수정
        userService.updateUser(testUser);
        Mockito.verify(userRepository, Mockito.times(1)).update(any());
        Mockito.verify(userRepository, Mockito.times(1)).countByUserId(anyString());
    }

    @Test
    @DisplayName("delete user")
    void deleteUser() {
        Mockito.when(userRepository.deleteByUserId(anyString())).thenReturn(1);
        Mockito.when(userRepository.countByUserId(anyString())).thenReturn(1);

        userService.deleteUser(testUser.getUserId());

        Mockito.verify(userRepository, Mockito.times(1)).deleteByUserId(anyString());
        Mockito.verify(userRepository, Mockito.times(1)).countByUserId(anyString());

    }

    @Test
    @DisplayName("login - success")
    void doLogin() {
        LoginResponse testLoginResponse = new LoginResponse(testUser.getUserId(), testUser.getUserName(), testUser.getUserAuth(), testUser.getLatestLoginAt());
        Mockito.when(userRepository.findByUserIdAndUserPassword(anyString(), anyString())).thenReturn(Optional.of(testLoginResponse));
        Mockito.when(userRepository.updateLatestLoginAtByUserId(anyString(), any())).thenReturn(1);

        LoginResponse loginResponse = userService.doLogin(testUser.getUserId(), testUser.getUserPassword());

        Assertions.assertEquals(testLoginResponse.getUserId(), loginResponse.getUserId());
        Assertions.assertEquals(testLoginResponse.getUserName(), loginResponse.getUserName());
        Assertions.assertEquals(testLoginResponse.getUserAuth(), loginResponse.getUserAuth());
        Assertions.assertEquals(testLoginResponse.getLatestLoginAt(), loginResponse.getLatestLoginAt());
        Mockito.verify(userRepository, Mockito.times(1)).findByUserIdAndUserPassword(anyString(), anyString());
        Mockito.verify(userRepository, Mockito.times(1)).updateLatestLoginAtByUserId(anyString(), any());
    }


    @Test
    @DisplayName("login fail")
    void doLogin_fail() {
        Mockito.when(userRepository.findByUserIdAndUserPassword(anyString(), anyString())).thenReturn(Optional.empty());
        Assertions.assertThrows(UserNotFoundException.class, () -> userService.doLogin(testUser.getUserId(), testUser.getUserPassword()));
        Mockito.verify(userRepository, Mockito.times(1)).findByUserIdAndUserPassword(anyString(), anyString());
    }
}
