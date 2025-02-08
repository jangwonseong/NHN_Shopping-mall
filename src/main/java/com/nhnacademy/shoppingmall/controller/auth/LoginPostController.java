package com.nhnacademy.shoppingmall.controller.auth;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.dto.LoginResponse;
import com.nhnacademy.shoppingmall.user.exception.UserNotFoundException;
import com.nhnacademy.shoppingmall.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RequestMapping(method = RequestMapping.Method.POST, value = "/loginAction.do")
public class LoginPostController implements BaseController {

    private UserService userService;

    public LoginPostController() {
        // 기본 생성자
    }

    public LoginPostController(UserService userService) {
        this.userService = userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String userId = req.getParameter("user_id");
        String password = req.getParameter("user_password");

        try {
            // 로그인 처리
            LoginResponse loginResponse = userService.doLogin(userId, password);

            HttpSession session = req.getSession();
            session.setAttribute("loginResponse", loginResponse);

            // User 객체 직접 조회 후 세션에 저장
            User user = userService.getUser(userId);
            session.setAttribute("user", user);

            session.setMaxInactiveInterval(60 * 60);
            return "redirect:/index.do";
        } catch (UserNotFoundException e) {
            req.setAttribute("loginError", "로그인 정보가 올바르지 않습니다.");
            return "shop/login/login_form";
        } catch (Exception e) {
            req.setAttribute("loginError", "로그인 처리 중 오류가 발생했습니다.");
            return "shop/login/login_form";
        }
    }
}
