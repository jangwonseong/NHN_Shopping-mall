package com.nhnacademy.shoppingmall.controller.order;

import com.nhnacademy.shoppingmall.cart.domain.CartItem;
import com.nhnacademy.shoppingmall.cart.service.CartItemService;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.mvc.controller.ServiceFactory;
import com.nhnacademy.shoppingmall.order.domain.Order;
import com.nhnacademy.shoppingmall.order.service.OrderService;
import com.nhnacademy.shoppingmall.orderItem.domain.OrderItem;
import com.nhnacademy.shoppingmall.user.domain.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequestMapping(method = RequestMapping.Method.POST, value = "/order/place.do")
public class PlaceOrderController implements BaseController {
    private final OrderService orderService;
    private final CartItemService cartItemService;

    public PlaceOrderController() {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        this.orderService = serviceFactory.getOrderService();
        this.cartItemService = serviceFactory.getCartItemService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");
        List<CartItem> cartItems = cartItemService.getCartItems(user.getUserId());

        List<OrderItem> orderItems = new ArrayList<>();
        int totalPrice = 0;

        for (CartItem item : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(item.getProductId());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPriceAtOrder(item.getProduct().getProductPrice());
            orderItems.add(orderItem);
            totalPrice += item.getQuantity() * item.get().getProductPrice();
        }

        Order order = new Order(
                UUID.randomUUID().toString(),
                user.getUserId(),
                totalPrice,
                LocalDateTime.now(),
                Order.OrderStatus.PENDING,
                orderItems
        );

        try {
            orderService.placeOrder(order);
            cartItemService.getCartItems(user.getUserId()).clear();
            return "redirect:/order/complete.do?orderId=" + order.getOrderId();
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            return "redirect:/order/checkout.do";
        }
    }
}
