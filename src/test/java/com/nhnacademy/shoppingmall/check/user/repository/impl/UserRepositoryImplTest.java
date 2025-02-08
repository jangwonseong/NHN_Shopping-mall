
package com.nhnacademy.shoppingmall.check.user.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.dto.LoginResponse;
import com.nhnacademy.shoppingmall.user.repository.UserRepository;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserRepositoryImplTest {
    UserRepository userRepository = new UserRepositoryImpl();
    User testUser;

    @BeforeEach
    void setUp() throws SQLException {
        DbConnectionThreadLocal.initialize();
        // 고유한 사용자 ID 생성
        String uniqueUserId = "nhnacademy-test-user-" + System.currentTimeMillis();
        testUser = new User(uniqueUserId, "nhn아카데미", "nhnacademy-test-password", "19900505", User.Auth.ROLE_USER, 1000000, LocalDateTime.now(), null);
        userRepository.save(testUser);
    }

    @AfterEach
    void tearDown() throws SQLException {
        // 테스트 데이터 삭제
        userRepository.deleteByUserId(testUser.getUserId());
        DbConnectionThreadLocal.reset();
    }

    @Test
    @Order(1)
    @DisplayName("로그인: user 조회 by userId and userPassword")
    void findByUserIdAndUserPassword() {
        // When
        Optional<LoginResponse> userOptional = userRepository.findByUserIdAndUserPassword(testUser.getUserId(), testUser.getUserPassword());

        // Then
        assertTrue(userOptional.isPresent());
        LoginResponse loginResponse = userOptional.get();
        assertEquals(testUser.getUserId(), loginResponse.getUserId());
        assertEquals(testUser.getUserName(), loginResponse.getUserName());
    }

    @Test
    @Order(2)
    @DisplayName("로그인 : sql injection 방어")
    void findByUserIdAndUserPassword_sql_injection() {
        // Given
        String password = "' or '1'='1";

        // When
        Optional<LoginResponse> userOptional = userRepository.findByUserIdAndUserPassword(testUser.getUserId(), password);

        // Then
        assertFalse(userOptional.isPresent());
    }

    @Test
    @Order(6)
    @DisplayName("user 삭제")
    void deleteByUserId() {
        // When
        int result = userRepository.deleteByUserId(testUser.getUserId());

        // Then
        assertAll(
                () -> assertEquals(1, result),
                () -> assertFalse(userRepository.findById(testUser.getUserId()).isPresent())
        );
    }


    @Test
    @Order(8)
    @DisplayName("최근 로그인시간 update")
    void updateLatestLoginAtByUserId() {
        // When
        int result = userRepository.updateLatestLoginAtByUserId(testUser.getUserId(), LocalDateTime.now());

        // Then
        assertEquals(1, result);
    }
}
