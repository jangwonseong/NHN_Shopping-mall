package com.nhnacademy.shoppingmall.orderItem.service.impl;

import com.nhnacademy.shoppingmall.orderItem.domain.OrderItem;
import com.nhnacademy.shoppingmall.orderItem.repository.OrderItemRepository;
import com.nhnacademy.shoppingmall.orderItem.service.OrderItemService;

import java.util.List;

public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepository;

    public OrderItemServiceImpl(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public void addOrderItem(OrderItem orderItem) {
        orderItemRepository.save(orderItem);
    }

    @Override
    public void removeOrderItemsByOrderId(String orderId) {
        orderItemRepository.deleteByOrderId(orderId);
    }

    @Override
    public List<OrderItem> getOrderItems(String orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }
}
