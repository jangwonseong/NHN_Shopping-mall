package com.nhnacademy.shoppingmall.product.exception;

public class DuplicateProductException extends RuntimeException {
    public DuplicateProductException(String productId){
        super("Product with id " + productId + " already exists");
    }
}
