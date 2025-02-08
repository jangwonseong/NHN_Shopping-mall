package com.nhnacademy.shoppingmall.category.repository;

import com.nhnacademy.shoppingmall.category.domain.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
    int save(Category category);
    Optional<Category> findById(String categoryId);
    List<Category> findAll();
    int update(Category category);
    int deleteById(String categoryId);
}
