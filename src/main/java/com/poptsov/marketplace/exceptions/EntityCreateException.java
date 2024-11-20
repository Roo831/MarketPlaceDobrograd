package com.poptsov.marketplace.exceptions;

public class EntityCreateException extends RuntimeException {
    public EntityCreateException(String message) {
        super(message);
    }
}
