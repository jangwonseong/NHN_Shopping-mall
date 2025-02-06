package com.nhnacademy.shoppingmall.order.exception;

public class InvalidPriceException extends RuntimeException {
    public InvalidPriceException(int price) {
        super("유효하지 않은 가격입니다: " + price);
    }
}