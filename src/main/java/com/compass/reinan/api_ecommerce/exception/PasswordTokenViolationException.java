package com.compass.reinan.api_ecommerce.exception;

public class PasswordTokenViolationException extends RuntimeException {
    public PasswordTokenViolationException(String message) {
        super(message);
    }
}
