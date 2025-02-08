package com.nhnacademy.shoppingmall.product.service.impl;

import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.ProductRepository;
import com.nhnacademy.shoppingmall.product.service.ProductService;

import java.util.Collections;
import java.util.List;

public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product getProduct(String productId) {
        return productRepository.findById(productId).orElseThrow(() -> new RuntimeException("품목을 찾을 수 없습니다."));
    }

    @Override
    public void saveProduct(Product product) {
        if (product == null || product.getProductId() == null) {
            throw new RuntimeException("품목이 유효하지 않습니다.");
        }
        productRepository.save(product);
    }

    @Override
    public void updateProduct(Product product) {
        if (product == null || product.getProductId() == null) {
            throw new RuntimeException("품목이 유효하지 않습니다.");
        }
        productRepository.update(product);
    }

    @Override
    public void deleteProduct(String productId) {
        if (productId == null) {
            throw new RuntimeException("품목이 유효하지 않습니다.");
        }
        productRepository.deleteById(productId);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    @Override
    public List<Product> getProductsBySearch(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return Collections.emptyList();
        }
        return productRepository.searchProducts(keyword);
    }
}
