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
    private static final String PRODUCT_COLUMNS =
            "product_id, category_id, product_name, description, product_price, product_stock";

    private Product createProductFromResultSet(ResultSet rs) throws SQLException {
        return new Product(
                rs.getString("product_id"),
                rs.getString("category_id"),
                rs.getString("product_name"),
                rs.getString("description"),
                rs.getInt("product_price"),
                rs.getInt("product_stock")
        );
    }

    @Override
    public int save(Product product) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = String.format("INSERT INTO products (%s) VALUES (?, ?, ?, ?, ?, ?)", PRODUCT_COLUMNS);

        try (PreparedStatement ptmt = connection.prepareStatement(sql)) {
            ptmt.setString(1, product.getProductId());
            ptmt.setString(2, product.getCategoryId());
            ptmt.setString(3, product.getProductName());
            ptmt.setString(4, product.getProductDescription());
            ptmt.setInt(5, product.getProductPrice());
            ptmt.setInt(6, product.getProductStock());
            return ptmt.executeUpdate();
        } catch (SQLException e) {
            log.error("품목 저장 실패 - 품목 ID: {}, SQL 상태: {}, 에러 코드: {}, 메시지: {}",
                    product.getProductId(), e.getSQLState(), e.getErrorCode(), e.getMessage(), e);
            throw new RuntimeException("품목 저장 실패", e);
        }
    }

    @Override
    public int deleteById(String productId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "DELETE FROM products WHERE product_id = ?";

        try (PreparedStatement ptmt = connection.prepareStatement(sql)) {
            ptmt.setString(1, productId);
            return ptmt.executeUpdate();
        } catch (SQLException e) {
            log.error("품목 삭제 실패 - 품목 ID: {}, SQL 상태: {}, 에러 코드: {}, 메시지: {}",
                    productId, e.getSQLState(), e.getErrorCode(), e.getMessage(), e);
            throw new RuntimeException("품목 삭제 실패", e);
        }
    }

    @Override
    public int update(Product product) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = """
                UPDATE products
                SET category_id = ?, 
                    product_name = ?, 
                    description = ?,
                    product_price = ?, 
                    product_stock = ?
                WHERE product_id = ?
                """;
        try (PreparedStatement ptmt = connection.prepareStatement(sql)) {
            ptmt.setString(1, product.getCategoryId());
            ptmt.setString(2, product.getProductName());
            ptmt.setString(3, product.getProductDescription());
            ptmt.setInt(4, product.getProductPrice());
            ptmt.setInt(5, product.getProductStock());
            ptmt.setString(6, product.getProductId());
            return ptmt.executeUpdate();
        } catch (SQLException e) {
            log.error("품목 수정 실패 - 품목 ID: {}, SQL 상태: {}, 에러 코드: {}, 메시지: {}",
                    product.getProductId(), e.getSQLState(), e.getErrorCode(), e.getMessage(), e);
            throw new RuntimeException("품목 수정 실패", e);
        }
    }

    @Override
    public Optional<Product> findById(String productId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = String.format("SELECT %s FROM products WHERE product_id = ?", PRODUCT_COLUMNS);

        try (PreparedStatement ptmt = connection.prepareStatement(sql)) {
            ptmt.setString(1, productId);
            try (ResultSet rs = ptmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(createProductFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            log.error("품목 조회 실패 - 품목 ID: {}, SQL 상태: {}, 에러 코드: {}, 메시지: {}",
                    productId, e.getSQLState(), e.getErrorCode(), e.getMessage(), e);
            throw new RuntimeException("품목 조회 실패", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Product> findByCategoryId(String categoryId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = String.format("SELECT %s FROM products WHERE category_id = ?", PRODUCT_COLUMNS);
        List<Product> productList = new ArrayList<>();

        try (PreparedStatement ptmt = connection.prepareStatement(sql)) {
            ptmt.setString(1, categoryId);
            try (ResultSet rs = ptmt.executeQuery()) {
                while (rs.next()) {
                    productList.add(createProductFromResultSet(rs));
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
        String sql = String.format("SELECT %s FROM products", PRODUCT_COLUMNS);
        List<Product> productList = new ArrayList<>();

        try (PreparedStatement ptmt = connection.prepareStatement(sql);
             ResultSet rs = ptmt.executeQuery()) {
            while (rs.next()) {
                productList.add(createProductFromResultSet(rs));
            }
        } catch (SQLException e) {
            log.error("전체 품목 조회 실패 - SQL 상태: {}, 에러 코드: {}, 메시지: {}",
                    e.getSQLState(), e.getErrorCode(), e.getMessage(), e);
            throw new RuntimeException("전체 품목 조회 실패", e);
        }
        return productList;
    }

    @Override
    public List<Product> searchProducts(String keyword) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = String.format("SELECT %s FROM products WHERE LOWER(product_name) LIKE ? OR LOWER(description) LIKE ?",
                PRODUCT_COLUMNS);
        List<Product> productList = new ArrayList<>();

        try (PreparedStatement ptmt = connection.prepareStatement(sql)) {
            String searchPattern = "%" + keyword.toLowerCase() + "%";
            ptmt.setString(1, searchPattern);
            ptmt.setString(2, searchPattern);

            try (ResultSet rs = ptmt.executeQuery()) {
                while (rs.next()) {
                    productList.add(createProductFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            log.error("상품 검색 실패 - 검색어: {}, SQL 상태: {}, 에러 코드: {}, 메시지: {}",
                    keyword, e.getSQLState(), e.getErrorCode(), e.getMessage(), e);
            throw new RuntimeException("상품 검색 실패", e);
        }
        return productList;
    }
}