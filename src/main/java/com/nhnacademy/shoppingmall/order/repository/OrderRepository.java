package com.nhnacademy.shoppingmall.order.repository;

import com.nhnacademy.shoppingmall.order.domain.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    int save(Order order);

    int update(Order order);

    int delete(String orderId);

    Optional<Order> findById(String orderId);

    List<Order> findByUserId(String userId);

    List<Order> findAll();

    List<Order> findByStatus(Order.OrderStatus status);

}
