package com.nhnacademy.shoppingmall.user.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.dto.LoginResponse;
import com.nhnacademy.shoppingmall.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
public class UserRepositoryImpl implements UserRepository {

    @Override
    public Optional<LoginResponse> findByUserIdAndUserPassword(String userId, String userPassword) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT user_id, user_name, user_auth, latest_login_at FROM users WHERE user_id = ? AND user_password = ?";

        log.debug("실행 쿼리: {}", sql);

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, userId);
            psmt.setString(2, userPassword);

            try (ResultSet rs = psmt.executeQuery()) {
                if (rs.next()) {
                    LoginResponse loginResponse = new LoginResponse(
                            rs.getString("user_id"),
                            rs.getString("user_name"),
                            User.Auth.valueOf(rs.getString("user_auth")),
                            rs.getTimestamp("latest_login_at") != null ? rs.getTimestamp("latest_login_at").toLocalDateTime() : null
                    );
                    return Optional.of(loginResponse);
                }
            }
        } catch (SQLException e) {
            log.error("로그인 실패 - 사용자 ID: {}, SQL 상태: {}, 에러 코드: {}, 메시지: {}",
                    userId, e.getSQLState(), e.getErrorCode(), e.getMessage(), e);
            throw new RuntimeException("로그인 처리 중 오류 발생", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findById(String userId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT user_id, user_name, user_birth, user_auth, user_point, created_at, latest_login_at FROM users WHERE user_id = ?";

        try (PreparedStatement ptmt = connection.prepareStatement(sql)) {
            ptmt.setString(1, userId);
            try (ResultSet rs = ptmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User(
                            rs.getString("user_id"),
                            rs.getString("user_name"),
                            null, // 패스워드 제외
                            rs.getString("user_birth"),
                            User.Auth.valueOf(rs.getString("user_auth")),
                            rs.getInt("user_point"),
                            rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null,
                            rs.getTimestamp("latest_login_at") != null ? rs.getTimestamp("latest_login_at").toLocalDateTime() : null
                    );
                    return Optional.of(user);
                }
            }
        } catch (SQLException e) {
            log.error("회원 조회 실패 - 사용자 ID: {}, SQL 상태: {}, 에러 코드: {}, 메시지: {}",
                    userId, e.getSQLState(), e.getErrorCode(), e.getMessage(), e);
            throw new RuntimeException("회원 정보 조회 실패", e);
        }
        return Optional.empty();
    }

    @Override
    public int save(User user) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "INSERT INTO users (user_id, user_name, user_password, user_birth, user_auth, user_point, created_at) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ptmt = connection.prepareStatement(sql)) {
            log.debug("save 메서드 시작 - 사용자 ID: {}", user.getUserId());
            log.debug("SQL: {}", sql);

            ptmt.setString(1, user.getUserId());
            ptmt.setString(2, user.getUserName());
            ptmt.setString(3, user.getUserPassword());
            ptmt.setString(4, user.getUserBirth());
            ptmt.setString(5, user.getUserAuth().toString());
            ptmt.setInt(6, user.getUserPoint());
            ptmt.setTimestamp(7, Timestamp.valueOf(user.getCreatedAt()));

            log.debug("파라미터: {}, {}, {}, {}, {}, {}, {}",
                    user.getUserId(), user.getUserName(), user.getUserPassword(),
                    user.getUserBirth(), user.getUserAuth(), user.getUserPoint(), user.getCreatedAt());

            int result = ptmt.executeUpdate();

            log.debug("executeUpdate 결과: {}", result);
            log.debug("save 메서드 종료 - 사용자 ID: {}", user.getUserId());

            return result;
        } catch (SQLIntegrityConstraintViolationException e) {
            log.error("중복 ID 시도 - 사용자 ID: {}", user.getUserId(), e);
            throw new RuntimeException("이미 존재하는 회원 ID", e);
        } catch (SQLException e) {
            log.error("회원 가입 실패 - 사용자 ID: {}, SQL 상태: {}, 에러 코드: {}, 메시지: {}",
                    user.getUserId(), e.getSQLState(), e.getErrorCode(), e.getMessage(), e);
            throw new RuntimeException("회원 가입 처리 실패", e);
        }
    }

    @Override
    public int deleteByUserId(String userId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "DELETE FROM users WHERE user_id = ?";

        try (PreparedStatement ptmt = connection.prepareStatement(sql)) {
            ptmt.setString(1, userId);
            return ptmt.executeUpdate();
        } catch (SQLException e) {
            log.error("삭제 실패 - 사용자 ID: {}, SQL 상태: {}, 에러 코드: {}, 메시지: {}",
                    userId, e.getSQLState(), e.getErrorCode(), e.getMessage(), e);
            throw new RuntimeException("회원 정보 삭제 실패", e);
        }
    }

    @Override
    public int update(User user) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "UPDATE users SET user_name=?, user_password=?, user_birth=?, user_auth=?, user_point=? WHERE user_id=?";

        try (PreparedStatement ptmt = connection.prepareStatement(sql)) {
            ptmt.setString(1, user.getUserName());
            ptmt.setString(2, user.getUserPassword());
            ptmt.setString(3, user.getUserBirth());
            ptmt.setString(4, user.getUserAuth().toString());
            ptmt.setInt(5, user.getUserPoint());
            ptmt.setString(6, user.getUserId());

            return ptmt.executeUpdate();
        } catch (SQLException e) {
            log.error("업데이트 실패 - 사용자 ID: {}, SQL 상태: {}, 에러 코드: {}, 메시지: {}",
                    user.getUserId(), e.getSQLState(), e.getErrorCode(), e.getMessage(), e);
            throw new RuntimeException("회원 정보 갱신 실패", e);
        }
    }

    @Override
    public int updateLatestLoginAtByUserId(String userId, LocalDateTime latestLoginAt) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "UPDATE users SET latest_login_at = ? WHERE user_id = ?";

        try (PreparedStatement ptmt = connection.prepareStatement(sql)) {
            ptmt.setTimestamp(1, Timestamp.valueOf(latestLoginAt));
            ptmt.setString(2, userId);
            return ptmt.executeUpdate();
        } catch (SQLException e) {
            log.error("로그인 시간 기록 실패 - 사용자 ID: {}, SQL 상태: {}, 에러 코드: {}, 메시지: {}",
                    userId, e.getSQLState(), e.getErrorCode(), e.getMessage(), e);
            throw new RuntimeException("로그인 시간 갱신 실패", e);
        }
    }

    @Override
    public int countByUserId(String userId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT COUNT(*) FROM users WHERE user_id = ?";

        try (PreparedStatement ptmt = connection.prepareStatement(sql)) {
            ptmt.setString(1, userId);

            try (ResultSet rs = ptmt.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        } catch (SQLException e) {
            log.error("회원 수 조회 실패 - 사용자 ID: {}, SQL 상태: {}, 에러 코드: {}, 메시지: {}",
                    userId, e.getSQLState(), e.getErrorCode(), e.getMessage(), e);
            throw new RuntimeException("회원 존재 여부 확인 실패", e);
        }
    }
}
