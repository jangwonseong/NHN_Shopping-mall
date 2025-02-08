package com.nhnacademy.shoppingmall.order.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.order.domain.Order;
import com.nhnacademy.shoppingmall.order.repository.OrderRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import java.sql.*;
import java.util.ArrayList;

public class OrderRepositoryImpl implements OrderRepository {

    // 📌 주문 저장
    @Override
    public int save(Order order) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        try {
            connection.setAutoCommit(false);

            saveOrder(order, connection);

            connection.commit();
            return 1;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                throw new RuntimeException("롤백 실패", rollbackEx);
            }
            throw new RuntimeException("주문 저장 실패", e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ignore) {}
        }
    }

    private void saveOrder(Order order, Connection connection) throws SQLException {
        String orderSql = "INSERT INTO orders (order_id, user_id, total_price, order_date, status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement orderPtmt = connection.prepareStatement(orderSql)) {
            orderPtmt.setString(1, order.getOrderId());
            orderPtmt.setString(2, order.getUserId());
            orderPtmt.setInt(3, order.getTotalPrice());
            orderPtmt.setTimestamp(4, Timestamp.valueOf(order.getOrderDate()));
            orderPtmt.setString(5, order.getStatus().name());
            orderPtmt.executeUpdate();
        }
    }

    // 📌 주문 업데이트
    @Override
    public int update(Order order) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        try {
            connection.setAutoCommit(false);

            updateOrder(order, connection);

            connection.commit();
            return 1;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                throw new RuntimeException("롤백 실패", rollbackEx);
            }
            throw new RuntimeException("주문 업데이트 실패", e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ignore) {}
        }
    }

    private void updateOrder(Order order, Connection connection) throws SQLException {
        String orderSql = "UPDATE orders SET user_id = ?, total_price = ?, order_date = ?, status = ? WHERE order_id = ?";
        try (PreparedStatement orderPtmt = connection.prepareStatement(orderSql)) {
            orderPtmt.setString(1, order.getUserId());
            orderPtmt.setInt(2, order.getTotalPrice());
            orderPtmt.setTimestamp(3, Timestamp.valueOf(order.getOrderDate()));
            orderPtmt.setString(4, order.getStatus().name());
            orderPtmt.setString(5, order.getOrderId());
            orderPtmt.executeUpdate();
        }
    }

    // 📌 주문 삭제
    @Override
    public int delete(String orderId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        try {
            connection.setAutoCommit(false);

            deleteOrder(orderId, connection);

            connection.commit();
            return 1;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                throw new RuntimeException("롤백 실패", rollbackEx);
            }
            throw new RuntimeException("주문 삭제 실패", e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ignore) {}
        }
    }

    private void deleteOrder(String orderId, Connection connection) throws SQLException {
        String orderSql = "DELETE FROM orders WHERE order_id = ?";
        try (PreparedStatement orderPtmt = connection.prepareStatement(orderSql)) {
            orderPtmt.setString(1, orderId);
            orderPtmt.executeUpdate();
        }
    }

    // 📌 특정 주문 조회
    @Override
    public Optional<Order> findById(String orderId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT order_id, user_id, total_price, order_date, status FROM orders WHERE order_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, orderId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Order order = mapOrder(rs);
                    return Optional.of(order);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("주문 조회 실패", e);
        }
        return Optional.empty();
    }

    // 📌 특정 사용자 주문 목록 조회
    @Override
    public List<Order> findByUserId(String userId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT order_id, user_id, total_price, order_date, status FROM orders WHERE user_id = ?";
        List<Order> orders = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    orders.add(mapOrder(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("사용자 주문 조회 실패", e);
        }
        return orders;
    }

    // 📌 모든 주문 조회
    @Override
    public List<Order> findAll() {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT order_id, user_id, total_price, order_date, status FROM orders";
        List<Order> orders = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                orders.add(mapOrder(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("전체 주문 조회 실패", e);
        }
        return orders;
    }

    // 📌 특정 상태의 주문 조회
    @Override
    public List<Order> findByStatus(Order.OrderStatus status) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT order_id, user_id, total_price, order_date, status FROM orders WHERE status = ?";
        List<Order> orders = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, status.name());
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    orders.add(mapOrder(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("주문 상태별 조회 실패", e);
        }
        return orders;
    }

    @Override
    public List<Order> findByUserIdWithPagination(String userId, int offset, int limit) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT order_id, user_id, total_price, order_date, status FROM orders WHERE user_id = ? LIMIT ? OFFSET ?";
        List<Order> orders = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, userId);
            pstmt.setInt(2, limit);
            pstmt.setInt(3, offset);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    orders.add(mapOrder(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("사용자 주문 조회(페이징) 실패", e);
        }
        return orders;
    }

    // 📌 ResultSet -> Order 변환 메서드
    private Order mapOrder(ResultSet rs) throws SQLException {
        return new Order(
                rs.getString("order_id"),
                rs.getString("user_id"),
                rs.getInt("total_price"),
                rs.getTimestamp("order_date").toLocalDateTime(),
                Order.OrderStatus.valueOf(rs.getString("status")),
                new ArrayList<>() // 기본적으로 빈 리스트로 초기화
        );
    }
}
