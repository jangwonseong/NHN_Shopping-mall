package com.nhnacademy.shoppingmall.controller.order;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.order.domain.Order;
import com.nhnacademy.shoppingmall.order.repository.impl.OrderRepositoryImpl;
import com.nhnacademy.shoppingmall.order.service.OrderService;
import com.nhnacademy.shoppingmall.order.service.impl.OrderServiceImpl;
import com.nhnacademy.shoppingmall.orderItem.repository.impl.OrderItemRepositoryImpl;
import com.nhnacademy.shoppingmall.orderItem.service.impl.OrderItemServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

@RequestMapping(method = RequestMapping.Method.GET, value = "/userOrders.do")
public class UserOrderController implements BaseController {
    private final OrderService orderService = new OrderServiceImpl(new OrderRepositoryImpl(), new OrderItemServiceImpl(new OrderItemRepositoryImpl()));
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String userId = req.getParameter("user_id");
        List<Order> orders = orderService.getOrdersByUserId(userId);
        req.setAttribute("orders", orders);
        return "shop/order/user_orders";
    }
}
