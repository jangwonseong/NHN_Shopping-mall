package com.nhnacademy.shoppingmall.product.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class ProductRepositoryImpl implements ProductRepository {

    @Override
    public int save(Product product) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "INSERT INTO products VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ptmt = connection.prepareStatement(sql)) {
            ptmt.setString(1, product.getProductId());
            ptmt.setString(2, product.getCategoryId());
            ptmt.setString(3, product.getProductName());
            ptmt.setString(4, product.getProductDescription());
            ptmt.setInt(5, product.getProductPrice());
            ptmt.setInt(6, product.getProductStock());
            return ptmt.executeUpdate();
        } catch (SQLException e) {
            log.error("품목 저장 실패 - 사용자 ID: {}, SQL 상태: {}, 에러 코드: {}, 메시지: {}",
                    product.getProductId(), e.getSQLState(), e.getErrorCode(), e.getMessage(), e);
            throw new RuntimeException("회원 가입 처리 실패", e);
        }
    }

    @Override
    public int deleteById(String productId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "DELETE FROM products WHERE productId = ?";

        try (PreparedStatement ptmt = connection.prepareStatement(sql)) {
            ptmt.setString(1, productId);
            return ptmt.executeUpdate();
        } catch (SQLException e) {
            log.error("품목 삭제 실패 - 사용자 ID: {}, SQL 상태: {}, 에러 코드: {}, 메시지: {}",
                    productId, e.getSQLState(), e.getErrorCode(), e.getMessage(), e);
            throw new RuntimeException("회원 가입 처리 실패", e);
        }
    }

    @Override
    public int update(Product product) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = """
                UPDATE products
                SET product_id = ?, 
                    category_id = ?, 
                    product_name = ?, 
                    description = ?,
                    product_price = ?, 
                    product_stock = ?
                WHERE product_id = ?
                """;
        try (PreparedStatement ptmt = connection.prepareStatement(sql)) {
            ptmt.setString(1, product.getProductId());
            ptmt.setString(2, product.getCategoryId());
            ptmt.setString(3, product.getProductName());
            ptmt.setString(4, product.getProductDescription());
            ptmt.setInt(5, product.getProductPrice());
            ptmt.setInt(6, product.getProductStock());
            return ptmt.executeUpdate();
        } catch (SQLException e) {
            log.error("업데이트 실패 - 품목 ID: {}, SQL 상태: {}, 에러 코드: {}, 메시지: {}",
                    product.getProductId(), e.getSQLState(), e.getErrorCode(), e.getMessage(), e);
            throw new RuntimeException("품목 정보 갱신 실패", e);
        }
    }

    @Override
    public Optional<Product> findById(String productId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT * FROM products WHERE product_id = ?";

        try (PreparedStatement ptmt = connection.prepareStatement(sql)) {
            ptmt.setString(1, productId);
            try (ResultSet rs = ptmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Product(
                            rs.getString("product_id"),
                            rs.getString("category_id"),
                            rs.getString("product_name"),
                            rs.getString("description"),
                            rs.getInt("product_price"),
                            rs.getInt("product_stock")
                    ));
                }
            }
        } catch (SQLException e) {
            log.error("품목 조회 실패 - 사용자 ID: {}, SQL 상태: {}, 에러 코드: {}, 메시지: {}",
                    productId, e.getSQLState(), e.getErrorCode(), e.getMessage(), e);
            throw new RuntimeException("품목 정보 조회 실패", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Product> findByCategoryId(String categoryId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT * FROM products WHERE category_id = ?";
        List<Product> productList = new ArrayList<>();

        try (PreparedStatement ptmt = connection.prepareStatement(sql)) {
            ptmt.setString(1, categoryId);
            try (ResultSet rs = ptmt.executeQuery()) {
                while (rs.next()) {
                    productList.add(new Product(
                            rs.getString("product_id"),
                            rs.getString("category_id"),
                            rs.getString("product_name"),
                            rs.getString("description"),
                            rs.getInt("product_price"),
                            rs.getInt("product_stock")
                    ));
                }
            }
        } catch (SQLException e) {
            log.error("카테고리별 품목 조회 실패 - 카테고리 ID: {}, SQL 상태: {}, 에러 코드: {}, 메시지: {}",
                    categoryId, e.getSQLState(), e.getErrorCode(), e.getMessage(), e);
            throw new RuntimeException("카테고리별 품목 조회 실패", e);
        }
        return productList;
    }

    @Override
    public List<Product> findAll() {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT * FROM products";
        List<Product> productList = new ArrayList<>();

        try (PreparedStatement ptmt = connection.prepareStatement(sql);
             ResultSet rs = ptmt.executeQuery()) {

            while (rs.next()) {
                // 쿼리 결과를 하나씩 Product 객체에 담아서 리스트에 추가
                productList.add(new Product(
                        rs.getString("product_id"),
                        rs.getString("category_id"),
                        rs.getString("product_name"),
                        rs.getString("description"),
                        rs.getInt("product_price"),
                        rs.getInt("product_stock")
                ));
            }

        } catch (SQLException e) {
            log.error("전체 품목 조회 실패 - SQL 상태: {}, 에러 코드: {}, 메시지: {}",
                    e.getSQLState(), e.getErrorCode(), e.getMessage(), e);
            throw new RuntimeException("전체 품목 정보 조회 실패", e);
        }
        return productList;
    }

    @Override
    public List<Product> searchProducts(String keyword) {
        return List.of();
    }
}
