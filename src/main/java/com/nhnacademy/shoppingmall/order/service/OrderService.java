package com.nhnacademy.shoppingmall.order.service;

import com.nhnacademy.shoppingmall.order.domain.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    void placeOrder(Order order);
    void cancelOrder(String orderId);
    Optional<Order> getOrderById(String orderId);
    List<Order> getOrdersByUserId(String userId);
    List<Order> getAllOrders();
    List<Order> getOrdersByUserIdWithPagination(String userId, int page, int size);
}
