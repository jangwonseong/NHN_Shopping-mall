package com.nhnacademy.shoppingmall.controller.order;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.mvc.controller.ServiceFactory;
import com.nhnacademy.shoppingmall.order.domain.Order;
import com.nhnacademy.shoppingmall.order.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;

@RequestMapping(method = RequestMapping.Method.GET, value = "/order/complete.do")
public class OrderCompleteController implements BaseController {
    private final OrderService orderService;

    public OrderCompleteController(OrderService orderService) {
        this.orderService = orderService;
    }
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String orderId = req.getParameter("orderId");
        Optional<Order> order = orderService.getOrderById(orderId);

        if (order.isPresent()) {
            req.setAttribute("order", order.get());
            return "order/complete";
        }
        return "redirect:/error.do";
    }
}