package com.nhnacademy.shoppingmall.product.repository;

import com.nhnacademy.shoppingmall.product.domain.Product;
import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    int save(Product product);
    int deleteById(String productId);
    int update(Product product);
    Optional<Product> findById(String productId);
    List<Product> findByCategoryId(String categoryId);
    List<Product> findAll();
    List<Product> searchProducts(String keyword);
}
