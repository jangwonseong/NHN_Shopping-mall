package com.nhnacademy.shoppingmall.product.repository.impl;

import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

public class ProductRespositoryImpl implements ProductRepository {

    @Override
    public int save(Product product) {
        return 0;
    }

    @Override
    public int deleteById(String productId) {
        return 0;
    }

    @Override
    public int update(Product product) {
        return 0;
    }

    @Override
    public Optional<Product> findById(String productId) {
        return Optional.empty();
    }

    @Override
    public List<Product> findByCategoryId(String categoryId) {
        return List.of();
    }

    @Override
    public List<Product> findAll() {
        return List.of();
    }

    @Override
    public List<Product> findByPriceRange(int minPrice, int maxPrice) {
        return List.of();
    }

    @Override
    public int countByProductId(String productId) {
        return 0;
    }
}
