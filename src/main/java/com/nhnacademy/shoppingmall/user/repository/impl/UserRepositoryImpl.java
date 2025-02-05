package com.nhnacademy.shoppingmall.user.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
public class UserRepositoryImpl implements UserRepository {

    /**
     * 사용자 ID와 비밀번호로 사용자 정보 조회
     * @param userId 사용자 ID
     * @param userPassword 사용자 비밀번호 (평문 저장 주의)
     * @return Optional<User> - 조회된 사용자 정보 (존재하지 않을 경우 empty)
     * @throws RuntimeException SQL 조회 실패 시 발생
     */
    @Override
    public Optional<User> findByUserIdAndUserPassword(String userId, String userPassword) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT * FROM users WHERE user_id = ? AND user_password = ?";

        log.debug("실행 쿼리: {}", sql);

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            // SQL 인젝션 방지를 위한 파라미터 바인딩
            psmt.setString(1, userId);
            psmt.setString(2, userPassword);

            try (ResultSet rs = psmt.executeQuery()) {
                if (rs.next()) {
                    // ResultSet -> User 객체 매핑
                    return Optional.of(new User(
                            rs.getString("user_id"),
                            rs.getString("user_name"),
                            rs.getString("user_password"),
                            rs.getString("user_birth"),
                            User.Auth.valueOf(rs.getString("user_auth")),
                            rs.getInt("user_point"),
                            rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null,
                            rs.getTimestamp("latest_login_at") != null ? rs.getTimestamp("latest_login_at").toLocalDateTime() : null
                    ));
                }
            }
        } catch (SQLException e) {
            log.error("로그인 실패 - 사용자 ID: {}, SQL 상태: {}, 에러 코드: {}, 메시지: {}",
                    userId, e.getSQLState(), e.getErrorCode(), e.getMessage(), e);
            throw new RuntimeException("로그인 처리 중 오류 발생", e);
        }
        return Optional.empty();
    }

    /**
     * 사용자 ID로 단일 사용자 정보 조회
     * @param userId 조회할 사용자 ID
     * @return Optional<User> - 조회된 사용자 정보
     * @throws RuntimeException 데이터 조회 실패 시 발생
     */
    @Override
    public Optional<User> findById(String userId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT * FROM users WHERE user_id = ?";

        try (PreparedStatement ptmt = connection.prepareStatement(sql)) {
            ptmt.setString(1, userId);
            try (ResultSet rs = ptmt.executeQuery()) {
                if (rs.next()) {
                    // 데이터베이스 NULL 값 안전 처리
                    return Optional.of(new User(
                            rs.getString("user_id"),
                            rs.getString("user_name"),
                            rs.getString("user_password"),
                            rs.getString("user_birth"),
                            User.Auth.valueOf(rs.getString("user_auth")),
                            rs.getInt("user_point"),
                            rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null,
                            rs.getTimestamp("latest_login_at") != null ? rs.getTimestamp("latest_login_at").toLocalDateTime() : null
                    ));
                }
            }
        } catch (SQLException e) {
            log.error("회원 조회 실패 - 사용자 ID: {}, SQL 상태: {}, 에러 코드: {}, 메시지: {}",
                    userId, e.getSQLState(), e.getErrorCode(), e.getMessage(), e);
            throw new RuntimeException("회원 정보 조회 실패", e);
        }
        return Optional.empty();
    }

    /**
     * 신규 사용자 등록
     * @param user 등록할 사용자 정보 객체
     * @return int - 영향받은 레코드 수 (1: 성공, 0: 실패)
     * @throws RuntimeException 중복 ID 또는 SQL 오류 발생 시
     */
    @Override
    public int save(User user) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "INSERT INTO users VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ptmt = connection.prepareStatement(sql)) {
            // 객체 값을 SQL 파라미터에 매핑
            ptmt.setString(1, user.getUserId());
            ptmt.setString(2, user.getUserName());
            ptmt.setString(3, user.getUserPassword()); // 주의: 평문 저장 방식
            ptmt.setString(4, user.getUserBirth());
            ptmt.setString(5, user.getUserAuth().toString());
            ptmt.setInt(6, user.getUserPoint());
            ptmt.setTimestamp(7, Timestamp.valueOf(user.getCreatedAt()));

            return ptmt.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            log.error("중복 ID 시도 - 사용자 ID: {}", user.getUserId(), e);
            throw new RuntimeException("이미 존재하는 회원 ID", e);
        } catch (SQLException e) {
            log.error("회원 가입 실패 - 사용자 ID: {}, SQL 상태: {}, 에러 코드: {}, 메시지: {}",
                    user.getUserId(), e.getSQLState(), e.getErrorCode(), e.getMessage(), e);
            throw new RuntimeException("회원 가입 처리 실패", e);
        }
    }

    /**
     * 사용자 정보 삭제
     * @param userId 삭제할 사용자 ID
     * @return int - 삭제된 레코드 수
     * @throws RuntimeException SQL 실행 실패 시
     */
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

    /**
     * 사용자 정보 업데이트
     * @param user 업데이트할 사용자 정보 객체
     * @return int - 영향받은 레코드 수
     * @throws RuntimeException SQL 오류 발생 시
     */
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

    /**
     * 최종 로그인 시간 업데이트
     * @param userId 사용자 ID
     * @param latestLoginAt 새로운 로그인 시간
     * @return int - 영향받은 레코드 수
     * @throws RuntimeException SQL 오류 발생 시
     */
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

    /**
     * 사용자 ID 존재 여부 확인
     * @param userId 확인할 사용자 ID
     * @return int - 해당 ID의 존재 개수 (0: 없음, 1: 존재)
     * @throws RuntimeException SQL 조회 실패 시
     */
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

