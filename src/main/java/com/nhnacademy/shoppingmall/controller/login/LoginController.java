package com.nhnacademy.shoppingmall.controller.login;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.user.domain.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Objects;

@RequestMapping(method = RequestMapping.Method.GET,value = "/login.do")
public class LoginController implements BaseController {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession(false);
        if(Objects.nonNull(session)) {
            User user = (User) session.getAttribute("user");
            if (Objects.nonNull(user)) {
                return "redirect:/index.do";
            }
        }
        return "shop/login/login_form";
    }
}
