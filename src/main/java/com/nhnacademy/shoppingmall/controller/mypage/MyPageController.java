package com.nhnacademy.shoppingmall.controller.mypage;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RequestMapping(method = RequestMapping.Method.GET, value = "/mypage.do")
public class MyPageController implements BaseController {
    private UserService userService;

    public MyPageController() {
        // 기본 생성자 추가
    }

    public MyPageController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        // 세션에서 로그인된 사용자 정보를 가져옵니다.
        User user = (User) req.getSession().getAttribute("user");

        if (user == null) {
            req.setAttribute("error", "로그인 후 접근할 수 있습니다.");
            return "redirect:/login.do";
        }

        // 사용자의 정보를 MyPage.jsp에 전달
        req.setAttribute("user", user);
        return "shop/mypage/mypage";
    }
}
