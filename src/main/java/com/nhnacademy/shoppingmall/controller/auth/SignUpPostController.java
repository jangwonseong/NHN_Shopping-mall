package com.nhnacademy.shoppingmall.controller.auth;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.user.dto.UserCreateRequest;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.UserRegisterService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@RequestMapping(method = RequestMapping.Method.POST, value = "/signup.do")
public class SignUpPostController implements BaseController {
    private final UserService userService;
    private final UserRegisterService userRegisterService;

    public SignUpPostController(UserService userService, UserRegisterService userRegisterService) {
        this.userService = userService;
        this.userRegisterService = userRegisterService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String userId = req.getParameter("userId");
        String userName = req.getParameter("userName");
        String password = req.getParameter("password");
        String userBirth = req.getParameter("userBirth");

        UserCreateRequest userCreateRequest = new UserCreateRequest(
                userId,
                userName,
                password,
                userBirth,
                10_00000
        );

        userRegisterService.registerUser(userCreateRequest);

        HttpSession session = req.getSession();
        session.setAttribute("signUpSuccess", "회원가입이 완료되었습니다.");
        return "redirect:/login.do";
    }
}
