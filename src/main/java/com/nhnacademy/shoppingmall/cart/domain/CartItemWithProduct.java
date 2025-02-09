package com.nhnacademy.shoppingmall.cart.domain;

import com.nhnacademy.shoppingmall.product.domain.Product;

public class CartItemWithProduct {
    private final CartItem cartItem;
    private final Product product;

    public CartItemWithProduct(CartItem cartItem, Product product) {
        this.cartItem = cartItem;
        this.product = product;
    }

    public String getUserId() {
        return cartItem.getUserId();
    }

    public String getProductId() {
        return cartItem.getProductId();
    }

    public int getQuantity() {
        return cartItem.getQuantity();
    }

    public Product getProduct() {
        return product;
    }
}
