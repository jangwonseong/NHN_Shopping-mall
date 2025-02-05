package com.nhnacademy.shoppingmall.product.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String productId){
        super("Product with ID " + productId + " not found");
    }

}
