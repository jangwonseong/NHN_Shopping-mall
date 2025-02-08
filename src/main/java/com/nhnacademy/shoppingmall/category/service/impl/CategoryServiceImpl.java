package com.nhnacademy.shoppingmall.category.service.impl;

import com.nhnacademy.shoppingmall.category.domain.Category;
import com.nhnacademy.shoppingmall.category.repository.CategoryRepository;
import com.nhnacademy.shoppingmall.category.service.CategoryService;
import java.util.List;
import java.util.Optional;

public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category getCategory(String categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("카테고리를 찾을 수 없습니다."));
    }

    @Override
    public void saveCategory(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public void updateCategory(Category category) {
        categoryRepository.update(category);
    }

    @Override
    public void deleteCategory(String categoryId) {
        categoryRepository.deleteById(categoryId);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}
