package com.nhnacademy.shoppingmall.product.exception;

public class ProductOutOfStockException extends RuntimeException {
    public ProductOutOfStockException(String productId) {
      super("Product " + productId + " is out of stock");
    }
}
