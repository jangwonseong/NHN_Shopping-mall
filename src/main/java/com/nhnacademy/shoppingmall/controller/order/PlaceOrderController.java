// package com.nhnacademy.shoppingmall.controller.order;
//
// import com.nhnacademy.shoppingmall.cart.domain.CartItem;
// import com.nhnacademy.shoppingmall.cart.domain.CartItemWithProduct;
// import com.nhnacademy.shoppingmall.cart.service.CartItemService;
// import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
// import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
// import com.nhnacademy.shoppingmall.common.mvc.controller.ServiceFactory;
// import com.nhnacademy.shoppingmall.order.domain.Order;
// import com.nhnacademy.shoppingmall.order.service.OrderService;
// import com.nhnacademy.shoppingmall.orderItem.domain.OrderItem;
// import com.nhnacademy.shoppingmall.product.domain.Product;
// import com.nhnacademy.shoppingmall.product.service.ProductService;
// import com.nhnacademy.shoppingmall.thread.channel.RequestChannel;
// import com.nhnacademy.shoppingmall.user.domain.User;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;
// import jakarta.servlet.http.HttpSession;
//
// import java.time.LocalDateTime;
// import java.util.ArrayList;
// import java.util.List;
// import java.util.UUID;
//
// @RequestMapping(method = RequestMapping.Method.POST, value = "/order/place.do")
// public class PlaceOrderController implements BaseController {
//     private final OrderService orderService;
//     private final CartItemService cartItemService;
//     private final RequestChannel requestChannel;
//
//     public PlaceOrderController(OrderService orderService,CartItemService cartItemService,RequestChannel requestChannel) {
//         this.orderService = orderService;
//         this.cartItemService = cartItemService;
//         this.requestChannel = requestChannel;
//     }
//
//     @Override
//     public String execute(HttpServletRequest req, HttpServletResponse resp) {
//         User user = (User) session.getAttribute("user");
//         List<CartItem> cartItems = cartItemService.getCartItems(user.getUserId());
//
//         Order order = createOrder(user, cartItems);
//
//         try {
//             orderService.placeOrder(order);
//             // 포인트 적립 요청 (비동기)
//             requestChannel.add(new PointRequest(user.getUserId(), (int)(order.getTotalPrice() * 0.1)));
//             // 장바구니 비우기
//             clearCart(user.getUserId(), cartItems);
//
//             return "redirect:/order/complete.do?orderId=" + order.getOrderId();
//         } catch (Exception e) {
//             req.setAttribute("error", e.getMessage());
//             return "redirect:/order/checkout.do";
//         }
//     }
// }