package com.nhnacademy.shoppingmall.orderItem.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class OrderItem {
    private String orderId;  // 주문 ID (FK)
    private String productId;  // 상품 ID (FK)
    private int quantity;  // 주문한 수량
    private int priceAtOrder;  // 주문 당시 가격

    public OrderItem(String orderId, String productId, int quantity, int priceAtOrder) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.priceAtOrder = priceAtOrder;
    }

    // Getter & Setter
}
