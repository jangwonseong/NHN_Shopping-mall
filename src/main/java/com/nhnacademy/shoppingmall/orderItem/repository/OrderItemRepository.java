package com.nhnacademy.shoppingmall.orderItem.repository;

import com.nhnacademy.shoppingmall.orderItem.domain.OrderItem;

import java.util.List;

public interface OrderItemRepository {
    int save(OrderItem orderItem);
    int deleteByOrderId(String orderId);
    List<OrderItem> findByOrderId(String orderId);
}
