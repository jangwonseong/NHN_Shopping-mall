package com.nhnacademy.shoppingmall.common.exception;

public class BadRequestException extends HttpExceiption {
    private static final int STATUS_CODE = 400;
    public BadRequestException(String message) {
        super(STATUS_CODE, message);
    }

    public BadRequestException(){
        super(STATUS_CODE);
    }
}
