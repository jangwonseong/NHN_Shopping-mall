package com.nhnacademy.shoppingmall.common.listener;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@WebListener
public class ApplicationListener implements ServletContextListener {
    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl());
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //todo application 시작시 테스트 계정인 admin,user 등록 . 존재하면 등록하지 않습니다.
        DbConnectionThreadLocal.initialize();

        User admin =  userService.getUser("admin");
        if(Objects.isNull(admin)){
            userService.saveUser(new User("admin","관리자","12345","19840505", User.Auth.ROLE_ADMIN,100_0000, LocalDateTime.now(),null));
        }
        User user = userService.getUser("user");
        if(Objects.isNull(user)){
            userService.saveUser(new User("user","회원","12345","19840505", User.Auth.ROLE_ADMIN,100_0000, LocalDateTime.now(),null));
        }

        DbConnectionThreadLocal.reset();
    }
}
