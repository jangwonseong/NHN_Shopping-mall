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

// 1. 체크아웃 페이지 표시 (장바구니 -> 체크아웃)
@RequestMapping(method = RequestMapping.Method.GET, value = "/order/checkout.do")
public class CheckOutController implements BaseController {
    private final CartItemService cartItemService;
    private final ProductService productService;

    public CheckOutController(CartItemService cartItemService, ProductService productService) {
        this.cartItemService = cartItemService;
        this.productService = productService;
    }
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            return "redirect:/login.do";
        }

        User user = (User) session.getAttribute("user");
        List<CartItem> cartItems = cartItemService.getCartItems(user.getUserId());
        Map<String, Product> productMap = new HashMap<>();
        int totalAmount = calculateTotalAmount(cartItems, productMap);

        req.setAttribute("cartItems", cartItems);
        req.setAttribute("productMap", productMap);
        req.setAttribute("totalAmount", totalAmount);
        req.setAttribute("userPoint", user.getUserPoint());

        return "order/checkout";
    }

    private int calculateTotalAmount(List<CartItem> cartItems, Map<String, Product> productMap) {
        return cartItems.stream()
                .mapToInt(item -> {
                    Product product = productMap.get(item.getProductId());
                    return product.getProductPrice() * item.getQuantity();
                })
                .sum();
    }

}