package com.nhnacademy.shoppingmall.order.domain;

import com.nhnacademy.shoppingmall.order.exception.InvalidPriceException;
import com.nhnacademy.shoppingmall.orderItem.domain.OrderItem;
import com.nhnacademy.shoppingmall.product.domain.Product;

import java.time.LocalDateTime;
import java.util.List;


public class Order {
    private String orderId;
    private String userId;
    private int totalPrice;
    private LocalDateTime orderDate;
    private OrderStatus status;
    private List<OrderItem> orderItems;

    public Order(String orderId, String userId, int totalPrice, LocalDateTime orderDate, OrderStatus status, List<OrderItem> orderItems) {
        this.orderId = orderId;
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.status = status;
        this.orderItems = orderItems;
    }
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<OrderItem> getOrderItems() {  // ✅ 올바르게 수정
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {  // ✅ 올바르게 수정
        this.orderItems = orderItems;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
    public enum OrderStatus {
        PENDING, PROCESSING, COMPLETED, CANCELED;
    }

}


