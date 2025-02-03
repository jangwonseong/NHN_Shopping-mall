package com.nhnacademy.shoppingmall.common.listener;

import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

import java.time.LocalDateTime;

@Slf4j
public class ApplicationListener implements ServletContextListener {
    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl());

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //todo#12 application 시작시 테스트 계정인 admin,user 등록합니다. 만약 존재하면 등록하지 않습니다.
        if (userService.getUser("user") == null) {
            userService.saveUser(new User("user", "user", "12345", "98-11-03", User.Auth.ROLE_USER, 1000000, LocalDateTime.now(), LocalDateTime.now().plusDays(1)));
            userService.saveUser(new User("admin", "admin", "12345", "98-11-03", User.Auth.ROLE_ADMIN, 1000000, LocalDateTime.now(), LocalDateTime.now().plusDays(1)));
        }
    }
}
