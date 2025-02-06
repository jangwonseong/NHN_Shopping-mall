package com.nhnacademy.shoppingmall.order.service.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.order.domain.Order;
import com.nhnacademy.shoppingmall.order.repository.OrderRepository;
import com.nhnacademy.shoppingmall.order.service.OrderService;
import com.nhnacademy.shoppingmall.orderItem.domain.OrderItem;
import com.nhnacademy.shoppingmall.orderItem.service.OrderItemService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemService orderItemService;

    public OrderServiceImpl(OrderRepository orderRepository, OrderItemService orderItemService) {
        this.orderRepository = orderRepository;
        this.orderItemService = orderItemService;
    }

    // 📌 주문 저장 (주문 + 주문 상품 함께 저장)
    @Override
    public void placeOrder(Order order) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        try {
            connection.setAutoCommit(false); // 트랜잭션 시작

            // 1. 주문 저장
            orderRepository.save(order);

            // 2. 주문에 포함된 상품(오더 아이템) 저장
            for (OrderItem item : order.getOrderItems()) {
                orderItemService.addOrderItem(item);
            }

            connection.commit(); // 트랜잭션 성공
        } catch (Exception e) {
            try {
                connection.rollback(); // 실패 시 롤백
            } catch (SQLException rollbackEx) {
                throw new RuntimeException("주문 롤백 실패", rollbackEx);
            }
            throw new RuntimeException("주문 저장 실패", e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ignore) {}
        }
    }

    // 📌 주문 취소 (주문 + 주문 상품 함께 삭제)
    @Override
    public void cancelOrder(String orderId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        try {
            connection.setAutoCommit(false); // 트랜잭션 시작

            // 1. 주문 상품 먼저 삭제
            orderItemService.removeOrderItemsByOrderId(orderId);

            // 2. 주문 삭제
            orderRepository.delete(orderId);

            connection.commit(); // 트랜잭션 성공
        } catch (Exception e) {
            try {
                connection.rollback(); // 실패 시 롤백
            } catch (SQLException rollbackEx) {
                throw new RuntimeException("주문 취소 롤백 실패", rollbackEx);
            }
            throw new RuntimeException("주문 취소 실패", e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ignore) {}
        }
    }

    // 📌 특정 주문 조회
    @Override
    public Optional<Order> getOrderById(String orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        order.ifPresent(o -> o.setOrderItems(orderItemService.getOrderItems(orderId)));
        return order;
    }

    // 📌 특정 사용자 주문 목록 조회
    @Override
    public List<Order> getOrdersByUserId(String userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        for (Order order : orders) {
            order.setOrderItems(orderItemService.getOrderItems(order.getOrderId()));
        }
        return orders;
    }

    // 📌 모든 주문 조회
    @Override
    public List<Order> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        for (Order order : orders) {
            order.setOrderItems(orderItemService.getOrderItems(order.getOrderId()));
        }
        return orders;
    }
}
