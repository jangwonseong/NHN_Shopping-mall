package com.nhnacademy.shoppingmall.controller.cart;


import com.nhnacademy.shoppingmall.cart.domain.CartItem;
import com.nhnacademy.shoppingmall.cart.service.CartItemService;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.mvc.controller.ServiceFactory;
import com.nhnacademy.shoppingmall.user.domain.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

@RequestMapping(method = RequestMapping.Method.GET, value = "/cart/clear.do")
public class CartClearController implements BaseController {
    private final CartItemService cartItemService;

    public CartClearController() {
        this.cartItemService = ServiceFactory.getInstance().getCartItemService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/login.do";
        }

        // 사용자의 모든 장바구니 아이템 삭제
        List<CartItem> cartItems = cartItemService.getCartItems(user.getUserId());
        for (CartItem item : cartItems) {
            cartItemService.removeFromCart(user.getUserId(), item.getProductId());
        }

        return "redirect:/cart/list.do";
    }
}