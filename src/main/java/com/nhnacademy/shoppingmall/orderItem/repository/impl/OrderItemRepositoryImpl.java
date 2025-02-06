package com.nhnacademy.shoppingmall.orderItem.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.orderItem.domain.OrderItem;
import com.nhnacademy.shoppingmall.orderItem.repository.OrderItemRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderItemRepositoryImpl implements OrderItemRepository {
    @Override
    public int save(OrderItem orderItem) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "INSERT INTO order_items (order_id, product_id, quantity, price_at_order) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ptmt = connection.prepareStatement(sql)) {
            ptmt.setString(1, orderItem.getOrderId());
            ptmt.setString(2, orderItem.getProductId());
            ptmt.setInt(3, orderItem.getQuantity());
            ptmt.setInt(4, orderItem.getPriceAtOrder());
            return ptmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("주문 상품 저장 실패", e);
        }
    }

    @Override
    public int deleteByOrderId(String orderId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "DELETE FROM order_items WHERE order_id = ?";

        try (PreparedStatement ptmt = connection.prepareStatement(sql)) {
            ptmt.setString(1, orderId);
            return ptmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("주문 상품 삭제 실패", e);
        }
    }

    @Override
    public List<OrderItem> findByOrderId(String orderId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT * FROM order_items WHERE order_id = ?";

        try (PreparedStatement ptmt = connection.prepareStatement(sql)) {
            ptmt.setString(1, orderId);
            ResultSet rs = ptmt.executeQuery();

            List<OrderItem> items = new ArrayList<>();
            while (rs.next()) {
                items.add(new OrderItem(
                        rs.getString("order_id"),
                        rs.getString("product_id"),
                        rs.getInt("quantity"),
                        rs.getInt("price_at_order")
                ));
            }
            return items;
        } catch (SQLException e) {
            throw new RuntimeException("주문 상품 조회 실패", e);
        }
    }
}
