package com.nhnacademy.shoppingmall.common.exception;

public class HttpExceiption extends RuntimeException {
    private final int statusCode;

    public HttpExceiption(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public HttpExceiption(int statusCode){
        super();
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
