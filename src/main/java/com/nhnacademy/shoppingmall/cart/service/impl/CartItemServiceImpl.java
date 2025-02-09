package com.nhnacademy.shoppingmall.cart.service.impl;

import com.nhnacademy.shoppingmall.cart.domain.CartItem;
import com.nhnacademy.shoppingmall.cart.domain.CartItemWithProduct;
import com.nhnacademy.shoppingmall.cart.repository.CartItemRepository;
import com.nhnacademy.shoppingmall.cart.service.CartItemService;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// CartItemServiceImpl.java
@Slf4j
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository cartItemRepository;
    private final ProductService productService;

    public CartItemServiceImpl(CartItemRepository cartItemRepository, ProductService productService) {
        this.cartItemRepository = cartItemRepository;
        this.productService = productService;
    }

    @Override
    public void addToCart(String userId, String productId, int quantity) {
        // 이미 장바구니에 있는지 확인
        if (cartItemRepository.existsByUserIdAndProductId(userId, productId)) {
            throw new IllegalStateException("이미 장바구니에 존재하는 상품입니다.");
        }

        // 상품 재고 확인
        Product product = productService.getProduct(productId);
        if (product == null || product.getProductStock() < quantity) {
            throw new IllegalStateException("상품의 재고가 부족합니다.");
        }

        CartItem cartItem = new CartItem(userId, productId, quantity);
        cartItemRepository.save(cartItem);
    }


    @Override
    public void removeFromCart(String userId, String productId) {
        cartItemRepository.deleteByUserIdAndProductId(userId, productId);
    }

    @Override
    public void updateQuantity(String userId, String productId, int quantity) {
        // 상품 재고 확인
        Product product = productService.getProduct(productId);
        if (product == null || product.getProductStock() < quantity) {
            throw new IllegalStateException("상품의 재고가 부족합니다.");
        }

        cartItemRepository.updateQuantity(userId, productId, quantity);
    }

    @Override
    public List<CartItem> getCartItems(String userId) {
        return cartItemRepository.findByUserId(userId);
    }

    @Override
    public List<CartItemWithProduct> getCartItems(String userId) {
        List<CartItem> cartItems = cartItemRepository.findByUserId(userId);
        List<CartItemWithProduct> result = new ArrayList<>();

        for (CartItem item : cartItems) {
            Product product = productService.getProduct(item.getProductId());
            result.add(new CartItemWithProduct(item, product));
        }

        return result;
    }

}