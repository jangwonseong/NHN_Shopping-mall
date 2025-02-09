package com.nhnacademy.shoppingmall.cart.service;

import com.nhnacademy.shoppingmall.cart.domain.CartItem;
import com.nhnacademy.shoppingmall.cart.domain.CartItemWithProduct;

import java.util.List;

public interface CartItemService {
    void addToCart(String userId, String productId, int quantity);
    void removeFromCart(String userId, String productId);
    void updateQuantity(String userId, String productId, int quantity);
    List<CartItem> getCartItems(String userId);
    List<CartItemWithProduct> getCartItemsWithProduct(String userId);
}