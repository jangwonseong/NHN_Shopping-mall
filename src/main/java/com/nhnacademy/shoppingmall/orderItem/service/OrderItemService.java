package com.nhnacademy.shoppingmall.orderItem.service;

import com.nhnacademy.shoppingmall.orderItem.domain.OrderItem;

import java.util.List;

public interface OrderItemService {
    void addOrderItem(OrderItem orderItem);
    void removeOrderItemsByOrderId(String orderId);
    List<OrderItem> getOrderItems(String orderId);
}
