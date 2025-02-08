package com.nhnacademy.shoppingmall.category.repository.impl;

import com.nhnacademy.shoppingmall.category.domain.Category;
import com.nhnacademy.shoppingmall.category.repository.CategoryRepository;
import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import lombok.extern.slf4j.Slf4j;
import java.sql.*;
import java.util.*;

@Slf4j
public class CategoryRepositoryImpl implements CategoryRepository {

    @Override
    public int save(Category category) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "INSERT INTO categories (category_id, category_name) VALUES (?, ?)";

        try (PreparedStatement ptmt = connection.prepareStatement(sql)) {
            ptmt.setString(1, category.getCategoryId());
            ptmt.setString(2, category.getCategoryName());
            return ptmt.executeUpdate();
        } catch (SQLException e) {
            log.error("카테고리 저장 실패 - 카테고리 ID: {}, SQL 상태: {}, 에러 코드: {}, 메시지: {}",
                    category.getCategoryId(), e.getSQLState(), e.getErrorCode(), e.getMessage(), e);
            throw new RuntimeException("카테고리 저장 실패", e);
        }
    }

    @Override
    public int deleteById(String categoryId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "DELETE FROM categories WHERE category_id = ?";

        try (PreparedStatement ptmt = connection.prepareStatement(sql)) {
            ptmt.setString(1, categoryId);
            return ptmt.executeUpdate();
        } catch (SQLException e) {
            log.error("카테고리 삭제 실패 - 카테고리 ID: {}, SQL 상태: {}, 에러 코드: {}, 메시지: {}",
                    categoryId, e.getSQLState(), e.getErrorCode(), e.getMessage(), e);
            throw new RuntimeException("카테고리 삭제 실패", e);
        }
    }

    @Override
    public int update(Category category) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "UPDATE categories SET category_name = ? WHERE category_id = ?";

        try (PreparedStatement ptmt = connection.prepareStatement(sql)) {
            ptmt.setString(1, category.getCategoryName());
            ptmt.setString(2, category.getCategoryId());
            return ptmt.executeUpdate();
        } catch (SQLException e) {
            log.error("카테고리 업데이트 실패 - 카테고리 ID: {}, SQL 상태: {}, 에러 코드: {}, 메시지: {}",
                    category.getCategoryId(), e.getSQLState(), e.getErrorCode(), e.getMessage(), e);
            throw new RuntimeException("카테고리 업데이트 실패", e);
        }
    }

    @Override
    public Optional<Category> findById(String categoryId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT * FROM categories WHERE category_id = ?";

        try (PreparedStatement ptmt = connection.prepareStatement(sql)) {
            ptmt.setString(1, categoryId);
            try (ResultSet rs = ptmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Category(
                            rs.getString("category_id"),
                            rs.getString("category_name")
                    ));
                }
            }
        } catch (SQLException e) {
            log.error("카테고리 조회 실패 - 카테고리 ID: {}, SQL 상태: {}, 에러 코드: {}, 메시지: {}",
                    categoryId, e.getSQLState(), e.getErrorCode(), e.getMessage(), e);
            throw new RuntimeException("카테고리 조회 실패", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Category> findAll() {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT * FROM categories";
        List<Category> categoryList = new ArrayList<>();

        try (PreparedStatement ptmt = connection.prepareStatement(sql);
             ResultSet rs = ptmt.executeQuery()) {
            while (rs.next()) {
                categoryList.add(new Category(
                        rs.getString("category_id"),
                        rs.getString("category_name")
                ));
            }
        } catch (SQLException e) {
            log.error("전체 카테고리 조회 실패 - SQL 상태: {}, 에러 코드: {}, 메시지: {}",
                    e.getSQLState(), e.getErrorCode(), e.getMessage(), e);
            throw new RuntimeException("전체 카테고리 조회 실패", e);
        }
        return categoryList;
    }
}
