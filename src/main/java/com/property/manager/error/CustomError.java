package com.property.manager.error;

import org.springframework.http.HttpStatus;

public enum CustomError {
    EXPIRED_TOKEN(HttpStatus.FORBIDDEN.value(), "Expired or invalid JWT token"),
    ACCESS_DENIED(HttpStatus.FORBIDDEN.value(), "Access Denied"),
    NO_PERMISSION(HttpStatus.FORBIDDEN.value(), "No Permission");

    private final int code;
    private final String message;

    CustomError(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }
}
