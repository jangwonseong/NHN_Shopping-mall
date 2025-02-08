package com.nhnacademy.shoppingmall.controller.cart;

import com.nhnacademy.shoppingmall.cart.service.CartItemService;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.mvc.controller.ServiceFactory;
import com.nhnacademy.shoppingmall.user.domain.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@RequestMapping(method = RequestMapping.Method.GET, value = "/cart/delete.do")
public class CartDeleteController implements BaseController {
    private final CartItemService cartItemService;

    public CartDeleteController() {
        this.cartItemService = ServiceFactory.getInstance().getCartItemService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/login.do";
        }

        String productId = req.getParameter("productId");
        cartItemService.removeFromCart(user.getUserId(), productId);

        return "redirect:/cart/list.do";
    }
}
