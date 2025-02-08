package com.nhnacademy.shoppingmall.address.repository.impl;

import com.nhnacademy.shoppingmall.address.domain.Address;
import com.nhnacademy.shoppingmall.address.repository.AddressRepository;
import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class AddressRepositoryImpl implements AddressRepository {

    @Override
    public Optional<Address> findById(int addressId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT address_id, user_id, street_address, postal_code FROM address WHERE address_id = ?";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, addressId);

            try (ResultSet rs = psmt.executeQuery()) {
                if (rs.next()) {
                    Address address = new Address(
                            rs.getInt("address_id"),
                            rs.getString("user_id"),
                            rs.getString("street_address"),
                            rs.getString("postal_code")
                    );
                    return Optional.of(address);
                }
            }
        } catch (SQLException e) {
            log.error("주소 조회 실패 - addressId: {}, SQL 상태: {}, 에러 코드: {}, 메시지: {}",
                    addressId, e.getSQLState(), e.getErrorCode(), e.getMessage(), e);
            throw new RuntimeException("주소 정보 조회 실패", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Address> findByUserId(String userId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT address_id, user_id, street_address, postal_code FROM address WHERE user_id = ?";
        List<Address> addressList = new ArrayList<>();

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, userId);

            try (ResultSet rs = psmt.executeQuery()) {
                while (rs.next()) {
                    Address address = new Address(
                            rs.getInt("address_id"),
                            rs.getString("user_id"),
                            rs.getString("street_address"),
                            rs.getString("postal_code")
                    );
                    addressList.add(address);
                }
            }
        } catch (SQLException e) {
            log.error("주소 목록 조회 실패 - userId: {}, SQL 상태: {}, 에러 코드: {}, 메시지: {}",
                    userId, e.getSQLState(), e.getErrorCode(), e.getMessage(), e);
            throw new RuntimeException("주소 목록 조회 실패", e);
        }
        return addressList;
    }

    @Override
    public int save(Address address) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "INSERT INTO address (user_id, street_address, postal_code) VALUES (?, ?, ?)";

        try (PreparedStatement psmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            psmt.setString(1, address.getUserId());
            psmt.setString(2, address.getStreetAddress());
            psmt.setString(3, address.getPostalCode());

            psmt.executeUpdate();

            try (ResultSet rs = psmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1); // auto-generated key
                }
            }
            return 0;

        } catch (SQLException e) {
            log.error("주소 저장 실패 - userId: {}, SQL 상태: {}, 에러 코드: {}, 메시지: {}",
                    address.getUserId(), e.getSQLState(), e.getErrorCode(), e.getMessage(), e);
            throw new RuntimeException("주소 정보 저장 실패", e);
        }
    }

    @Override
    public int update(Address address) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "UPDATE address SET street_address = ?, postal_code = ? WHERE address_id = ?";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, address.getStreetAddress());
            psmt.setString(2, address.getPostalCode());
            psmt.setInt(3, address.getAddressId());

            return psmt.executeUpdate();
        } catch (SQLException e) {
            log.error("주소 갱신 실패 - addressId: {}, SQL 상태: {}, 에러 코드: {}, 메시지: {}",
                    address.getAddressId(), e.getSQLState(), e.getErrorCode(), e.getMessage(), e);
            throw new RuntimeException("주소 정보 갱신 실패", e);
        }
    }

    @Override
    public int deleteById(int addressId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "DELETE FROM address WHERE address_id = ?";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, addressId);

            return psmt.executeUpdate();
        } catch (SQLException e) {
            log.error("주소 삭제 실패 - addressId: {}, SQL 상태: {}, 에러 코드: {}, 메시지: {}",
                    addressId, e.getSQLState(), e.getErrorCode(), e.getMessage(), e);
            throw new RuntimeException("주소 정보 삭제 실패", e);
        }
    }
}
