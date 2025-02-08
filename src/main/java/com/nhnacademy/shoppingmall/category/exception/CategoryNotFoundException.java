package com.nhnacademy.shoppingmall.category.exception;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(String categoryId) {
        super("category with id " + categoryId + " not found");
    }
}
