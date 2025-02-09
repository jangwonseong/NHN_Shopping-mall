package com.nhnacademy.shoppingmall.category.domain;

public class Category {
    private String categoryId;
    private String categoryName;
    private int orderSeq;

    public Category(String categoryId, String categoryName, int orderSeq) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.orderSeq = orderSeq;
    }

    // 기존 생성자 오버로딩
    public Category(String categoryId, String categoryName) {
        this(categoryId, categoryName, 0);  // 기본 정렬 순서는 0
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getOrderSeq() {
        return orderSeq;
    }

    public void setOrderSeq(int orderSeq) {
        this.orderSeq = orderSeq;
    }
}
