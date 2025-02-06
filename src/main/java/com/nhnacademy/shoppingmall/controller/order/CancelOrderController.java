package com.nhnacademy.shoppingmall.controller.order;


import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.order.repository.impl.OrderRepositoryImpl;
import com.nhnacademy.shoppingmall.order.service.OrderService;
import com.nhnacademy.shoppingmall.order.service.impl.OrderServiceImpl;
import com.nhnacademy.shoppingmall.orderItem.repository.impl.OrderItemRepositoryImpl;
import com.nhnacademy.shoppingmall.orderItem.service.impl.OrderItemServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RequestMapping(method = RequestMapping.Method.POST, value = "/cancelOrder.do")
public class CancelOrderController implements BaseController {

    private final OrderService orderService = new OrderServiceImpl(new OrderRepositoryImpl(), new OrderItemServiceImpl(new OrderItemRepositoryImpl()));

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String orderId = req.getParameter("order_id");
        orderService.cancelOrder(orderId);
        return "redirect:/order_cancel_success.do";
    }
}
