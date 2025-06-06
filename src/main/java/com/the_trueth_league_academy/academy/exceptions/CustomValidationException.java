package com.the_trueth_league_academy.academy.exceptions;

public class CustomValidationException extends RuntimeException {
    private final int statusCode;

    public CustomValidationException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}