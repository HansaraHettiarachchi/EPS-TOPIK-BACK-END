package com.the_trueth_league_academy.academy.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    private final int statusCode;

    public ResourceNotFoundException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}