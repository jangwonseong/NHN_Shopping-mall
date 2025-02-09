// package com.nhnacademy.shoppingmall.controller.order;

// import com.nhnacademy.shoppingmall.cart.domain.CartItem;
// import com.nhnacademy.shoppingmall.cart.service.CartItemService;
// import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
// import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
// import com.nhnacademy.shoppingmall.common.mvc.controller.ServiceFactory;
// import com.nhnacademy.shoppingmall.product.domain.Product;
// import com.nhnacademy.shoppingmall.product.service.ProductService;
// import com.nhnacademy.shoppingmall.user.domain.User;
// import com.nhnacademy.shoppingmall.user.service.UserService;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;
// import jakarta.servlet.http.HttpSession;

// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;

// @RequestMapping(method = RequestMapping.Method.POST, value = "/order/payment.do")
// public class PaymentController implements BaseController {
//     private final CartItemService cartItemService;
//     private final ProductService productService;

//     public PaymentController(CartItemService cartItemService, ProductService productService) {
//         this.cartItemService = cartItemService;
//         this.productService = productService;
//     }
//     @Override
//     public String execute(HttpServletRequest req, HttpServletResponse resp) {
//         User user = (User) req.getSession().getAttribute("user");
//         List<CartItem> cartItems = cartItemService.getCartItems(user.getUserId());

//         // 재고 및 포인트 확인
//         if (!validateOrder(user, cartItems)) {
//             return "redirect:/order/checkout.do";
//         }

//         return "redirect:/order/place.do";
//     }

//     private boolean validateOrder(User user, List<CartItem> cartItems) {
//         // 재고 및 포인트 검증 로직
//     }
// }

//@RequestMapping(method = RequestMapping.Method.POST, value = "/order/payment.do")
//public class PaymentController implements BaseController {
//    private final CartItemService cartItemService;
//    private final ProductService productService;
//    private final PointService pointService;
//
//    public PaymentController() {
//        ServiceFactory serviceFactory = ServiceFactory.getInstance();
//        this.cartItemService = serviceFactory.getCartItemService();
//        this.productService = serviceFactory.getProductService();
//        this.pointService = serviceFactory.getPointService();
//    }
//
//    @Override
//    public String execute(HttpServletRequest req, HttpServletResponse resp) {
//        HttpSession session = req.getSession(false);
//        User user = (User) session.getAttribute("user");
//        List<CartItem> cartItems = cartItemService.getCartItems(user.getUserId());
//
//        try {
//            // 총 결제 금액 계산
//            int totalAmount = calculateTotalAmount(cartItems);
//
//            // 포인트 잔액 확인
//            if (pointService.getUserPoints(user.getUserId()) < totalAmount) {
//                req.setAttribute("error", "포인트가 부족합니다.");
//                return "redirect:/cart/list.do";
//            }
//
//            // 포인트 사용 처리
//            String orderId = UUID.randomUUID().toString();
//            pointService.usePoints(user.getUserId(), totalAmount, orderId);
//
//            // 포인트 적립 처리 (10%)
//            pointService.earnPoints(user.getUserId(), totalAmount, orderId);
//
//            return "redirect:/order/place.do?orderId=" + orderId;
//        } catch (Exception e) {
//            req.setAttribute("error", e.getMessage());
//            return "redirect:/cart/list.do";
//        }
//    }
//
//    private int calculateTotalAmount(List<CartItem> cartItems) {
//        return cartItems.stream()
//                .mapToInt(item -> {
//                    Product product = productService.getProduct(item.getProductId());
//                    return product.getProductPrice() * item.getQuantity();
//                })
//                .sum();
//    }
//}
