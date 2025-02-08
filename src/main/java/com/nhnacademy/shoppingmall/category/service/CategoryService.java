package com.nhnacademy.shoppingmall.category.service;

import com.nhnacademy.shoppingmall.category.domain.Category;

import java.util.List;

public interface CategoryService {
    void saveCategory(Category category);
    Category getCategory(String categoryId);
    void updateCategory(Category category);
    void deleteCategory(String categoryId);
    List<Category> getAllCategories();

}
