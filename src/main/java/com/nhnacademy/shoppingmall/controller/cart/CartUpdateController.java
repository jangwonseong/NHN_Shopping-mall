package com.nhnacademy.shoppingmall.controller.cart;

import com.nhnacademy.shoppingmall.cart.service.CartItemService;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.mvc.controller.ServiceFactory;
import com.nhnacademy.shoppingmall.user.domain.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// 장바구니 수량 수정 컨트롤러
@RequestMapping(method = RequestMapping.Method.POST, value = "/cart/update.do")
public class CartUpdateController implements BaseController {
    private final CartItemService cartItemService;

    public CartUpdateController() {
        this.cartItemService = ServiceFactory.getInstance().getCartItemService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/login.do";
        }

        String productId = req.getParameter("productId");
        int quantity = Integer.parseInt(req.getParameter("quantity"));

        try {
            cartItemService.updateQuantity(user.getUserId(), productId, quantity);
        } catch (IllegalStateException e) {
            req.setAttribute("error", e.getMessage());
        }

        return "redirect:/cart/list.do";
    }
}