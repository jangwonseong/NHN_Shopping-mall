package com.nhnacademy.shoppingmall.cart.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class CartItem {
    private String userId;
    private String productId;
    private int quantity;
    private LocalDateTime createdAt;

    public CartItem(String userId, String productId, int quantity) {
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
        this.createdAt = LocalDateTime.now();
    }
}
