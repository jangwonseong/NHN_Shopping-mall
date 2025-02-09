package com.nhnacademy.shoppingmall.controller.user;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.mvc.controller.ServiceFactory;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping(method = RequestMapping.Method.GET, value = "/admin/user/list.do")
public class AdminUserListController implements BaseController {
    private static final int ITEMS_PER_PAGE = 10;  // 페이지당 표시할 회원 수
    private final UserService userService;

    public AdminUserListController() {
        this.userService = ServiceFactory.getInstance().getUserService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        int currentPage = getCurrentPage(req.getParameter("page"));

        List<User> allUsers = userService.getAllUsers();

        List<User> adminUsers = allUsers.stream()
                .filter(user -> User.Auth.ROLE_ADMIN.equals(user.getUserAuth()))
                .sorted(Comparator.comparing(User::getCreatedAt).reversed())
                .collect(Collectors.toList());

        List<User> normalUsers = allUsers.stream()
                .filter(user -> User.Auth.ROLE_USER.equals(user.getUserAuth()))
                .sorted(Comparator.comparing(User::getCreatedAt).reversed())
                .collect(Collectors.toList());

        int totalUsers = allUsers.size();
        int totalPages = (int) Math.ceil((double) totalUsers / ITEMS_PER_PAGE);
        int start = (currentPage - 1) * ITEMS_PER_PAGE;
        int end = Math.min(start + ITEMS_PER_PAGE, totalUsers);

        req.setAttribute("adminUsers", adminUsers);
        req.setAttribute("normalUsers", normalUsers);
        req.setAttribute("currentPage", currentPage);
        req.setAttribute("totalPages", totalPages);

        return "admin/user/user_list";
    }

    private int getCurrentPage(String pageParam) {
        try {
            int page = Integer.parseInt(pageParam);
            return page > 0 ? page : 1;
        } catch (NumberFormatException e) {
            return 1;
        }
    }
}