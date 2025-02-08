package com.nhnacademy.shoppingmall.cart.repository.impl;

import com.nhnacademy.shoppingmall.cart.domain.CartItem;
import com.nhnacademy.shoppingmall.cart.repository.CartItemRepository;
import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CartItemRepositoryImpl implements CartItemRepository {
    @Override
    public void save(CartItem cartItem) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "INSERT INTO cart_items (user_id, product_id, quantity, created_at) VALUES (?, ?, ?, ?)";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, cartItem.getUserId());
            psmt.setString(2, cartItem.getProductId());
            psmt.setInt(3, cartItem.getQuantity());
            psmt.setTimestamp(4, Timestamp.valueOf(cartItem.getCreatedAt()));

            psmt.executeUpdate();
        } catch (SQLException e) {
            log.error("장바구니 추가 실패", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteByUserIdAndProductId(String userId, String productId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "DELETE FROM cart_items WHERE user_id = ? AND product_id = ?";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, userId);
            psmt.setString(2, productId);

            psmt.executeUpdate();
        } catch (SQLException e) {
            log.error("장바구니 삭제 실패", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateQuantity(String userId, String productId, int quantity) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "UPDATE cart_items SET quantity = ? WHERE user_id = ? AND product_id = ?";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, quantity);
            psmt.setString(2, userId);
            psmt.setString(3, productId);

            psmt.executeUpdate();
        } catch (SQLException e) {
            log.error("장바구니 수량 업데이트 실패", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<CartItem> findByUserId(String userId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT * FROM cart_items WHERE user_id = ?";
        List<CartItem> cartItems = new ArrayList<>();

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, userId);

            try (ResultSet rs = psmt.executeQuery()) {
                while (rs.next()) {
                    CartItem cartItem = new CartItem(
                            rs.getString("user_id"),
                            rs.getString("product_id"),
                            rs.getInt("quantity")
                    );
                    cartItems.add(cartItem);
                }
            }
        } catch (SQLException e) {
            log.error("장바구니 목록 조회 실패", e);
            throw new RuntimeException(e);
        }
        return cartItems;
    }

    @Override
    public boolean existsByUserIdAndProductId(String userId, String productId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT COUNT(*) FROM cart_items WHERE user_id = ? AND product_id = ?";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, userId);
            psmt.setString(2, productId);

            try (ResultSet rs = psmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            log.error("장바구니 상품 존재 여부 확인 실패", e);
            throw new RuntimeException(e);
        }
        return false;
    }
}