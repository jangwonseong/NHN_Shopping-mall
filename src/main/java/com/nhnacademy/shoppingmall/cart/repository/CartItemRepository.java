package com.nhnacademy.shoppingmall.cart.repository;

import com.nhnacademy.shoppingmall.cart.domain.CartItem;

import java.util.List;

public interface CartItemRepository {
    // 장바구니에 상품 추가
    void save(CartItem cartItem);

    // 장바구니에서 상품 삭제
    void deleteByUserIdAndProductId(String userId, String productId);

    // 장바구니 상품 수량 업데이트
    void updateQuantity(String userId, String productId, int quantity);

    // 사용자의 장바구니 목록 조회
    List<CartItem> findByUserId(String userId);

    // 장바구니에 같은 상품이 있는지 확인
    boolean existsByUserIdAndProductId(String userId, String productId);
}
