package com.nhnacademy.shoppingmall.point.repository.impl;

import com.nhnacademy.shoppingmall.point.domain.Point;
import com.nhnacademy.shoppingmall.point.repository.PointRepository;
import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class PointRepositoryImpl implements PointRepository {
    @Override
    public void save(Point point) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "INSERT INTO Points (point_id, user_id, amount, type, order_id, created_at, description) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, point.getPointId());
            psmt.setString(2, point.getUserId());
            psmt.setInt(3, point.getAmount());
            psmt.setString(4, point.getType().toString());
            psmt.setString(5, point.getOrderId());
            psmt.setTimestamp(6, Timestamp.valueOf(point.getCreatedAt()));
            psmt.setString(7, point.getDescription());

            psmt.executeUpdate();
        } catch (SQLException e) {
            log.error("포인트 저장 실패: {}", e.getMessage(), e);
            throw new RuntimeException("포인트 저장 실패", e);
        }
    }

    @Override
    public List<Point> findByUserId(String userId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT * FROM Points WHERE user_id = ? ORDER BY created_at DESC";
        List<Point> points = new ArrayList<>();

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, userId);

            try (ResultSet rs = psmt.executeQuery()) {
                while (rs.next()) {
                    points.add(createPointFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            log.error("포인트 조회 실패: {}", e.getMessage(), e);
            throw new RuntimeException("포인트 조회 실패", e);
        }
        return points;
    }

    @Override
    public List<Point> findByUserIdWithPaging(String userId, int offset, int limit) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT * FROM Points WHERE user_id = ? ORDER BY created_at DESC LIMIT ? OFFSET ?";
        List<Point> points = new ArrayList<>();

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, userId);
            psmt.setInt(2, limit);
            psmt.setInt(3, offset);

            try (ResultSet rs = psmt.executeQuery()) {
                while (rs.next()) {
                    points.add(createPointFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            log.error("포인트 페이징 조회 실패: {}", e.getMessage(), e);
            throw new RuntimeException("포인트 페이징 조회 실패", e);
        }
        return points;
    }

    @Override
    public int getTotalPoints(String userId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT COALESCE(SUM(CASE WHEN type = 'EARN' THEN amount ELSE -amount END), 0) " +
                "FROM Points WHERE user_id = ?";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, userId);

            try (ResultSet rs = psmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                return 0;
            }
        } catch (SQLException e) {
            log.error("포인트 합계 조회 실패: {}", e.getMessage(), e);
            throw new RuntimeException("포인트 합계 조회 실패", e);
        }
    }

    private Point createPointFromResultSet(ResultSet rs) throws SQLException {
        return new Point(
                rs.getString("user_id"),
                rs.getInt("amount"),
                Point.PointType.valueOf(rs.getString("type")),
                rs.getString("order_id"),
                rs.getString("description")
        );
    }
}
