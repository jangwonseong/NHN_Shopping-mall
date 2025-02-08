package com.nhnacademy.shoppingmall.controller.cart;

import com.nhnacademy.shoppingmall.cart.domain.CartItem;
import com.nhnacademy.shoppingmall.cart.service.CartItemService;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.mvc.controller.ServiceFactory;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import com.nhnacademy.shoppingmall.user.domain.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping(method = RequestMapping.Method.GET, value = "/cart/list.do")
public class CartListController implements BaseController {
    private final CartItemService cartItemService;
    private final ProductService productService;

    public CartListController() {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        this.cartItemService = serviceFactory.getCartItemService();
        this.productService = serviceFactory.getProductService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        // 로그인 체크
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/login.do";
        }

        // 장바구니 아이템 조회
        List<CartItem> cartItems = cartItemService.getCartItems(user.getUserId());

        // 각 장바구니 아이템의 상품 정보 조회
        Map<String, Product> productMap = new HashMap<>();
        for (CartItem item : cartItems) {
            Product product = productService.getProduct(item.getProductId());
            productMap.put(item.getProductId(), product);
        }

        // 총 금액 계산
        int totalAmount = cartItems.stream()
                .mapToInt(item -> {
                    Product product = productMap.get(item.getProductId());
                    return product.getProductPrice() * item.getQuantity();
                })
                .sum();

        // request에 데이터 설정
        req.setAttribute("cartItems", cartItems);
        req.setAttribute("productMap", productMap);
        req.setAttribute("totalAmount", totalAmount);

        return "shop/cart/cart_list";
    }
}
