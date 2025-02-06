package com.nhnacademy.shoppingmall.controller.order;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.order.domain.Order;
import com.nhnacademy.shoppingmall.order.repository.impl.OrderRepositoryImpl;
import com.nhnacademy.shoppingmall.order.service.OrderService;
import com.nhnacademy.shoppingmall.order.service.impl.OrderServiceImpl;
import com.nhnacademy.shoppingmall.orderItem.domain.OrderItem;
import com.nhnacademy.shoppingmall.orderItem.repository.impl.OrderItemRepositoryImpl;
import com.nhnacademy.shoppingmall.orderItem.service.impl.OrderItemServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequestMapping(method = RequestMapping.Method.POST, value = "/placeOrder.do")
public class PlaceOrderController implements BaseController {

    private final OrderService orderService = new OrderServiceImpl(new OrderRepositoryImpl(), new OrderItemServiceImpl(new OrderItemRepositoryImpl()));
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String userId = req.getParameter("userId");
        int totalPrice = Integer.parseInt(req.getParameter("totalPrice"));

        List<OrderItem> orderItems = new ArrayList<>();

        Order order = new Order(UUID.randomUUID().toString(), userId, totalPrice, LocalDateTime.now(), Order.OrderStatus.PENDING, orderItems);
        orderService.placeOrder(order);

        return "redirect:/order_success.do";
    }
}
