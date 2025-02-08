package com.nhnacademy.shoppingmall.controller.user;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.mvc.controller.ServiceFactory;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RequestMapping(method = RequestMapping.Method.POST, value = "/update.do")
public class UserUpdateController implements BaseController {

    private UserService userService;

    public UserUpdateController() {
        this.userService = ServiceFactory.getInstance().getUserService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        // 폼에서 받은 사용자 정보
        String userName = req.getParameter("userName");
        String userPassword = req.getParameter("userPassword");
        String userBirth = req.getParameter("userBirth");

        // 세션에서 로그인된 사용자 정보 가져오기
        User user = (User) req.getSession().getAttribute("user");

        if (user == null) {
            req.setAttribute("error", "로그인 후 접근할 수 있습니다.");
            return "redirect:/login.do";
        }

        // 수정된 정보로 User 객체 갱신
        user.setUserName(userName);
        user.setUserPassword(userPassword);
        user.setUserBirth(userBirth);

        // DB 업데이트
        userService.updateUser(user);

        return "redirect:/login.do";
    }
}
