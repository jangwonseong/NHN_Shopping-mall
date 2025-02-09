package com.nhnacademy.shoppingmall.controller.order;

import com.nhnacademy.shoppingmall.cart.domain.CartItem;
import com.nhnacademy.shoppingmall.cart.service.CartItemService;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.mvc.controller.ServiceFactory;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping(method = RequestMapping.Method.POST, value = "/order/payment.do")
public class PaymentController implements BaseController {
    private final CartItemService cartItemService;
    private final ProductService productService;

    public PaymentController(CartItemService cartItemService, ProductService productService) {
        this.cartItemService = cartItemService;
        this.productService = productService;
    }
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        User user = (User) req.getSession().getAttribute("user");
        List<CartItem> cartItems = cartItemService.getCartItems(user.getUserId());

        // 재고 및 포인트 확인
        if (!validateOrder(user, cartItems)) {
            return "redirect:/order/checkout.do";
        }

        return "redirect:/order/place.do";
    }

    private boolean validateOrder(User user, List<CartItem> cartItems) {
        // 재고 및 포인트 검증 로직
    }
}