package com.poptsov.marketplace.exceptions;

public class EntityAlreadyException extends RuntimeException {
    public EntityAlreadyException(String message) {
        super(message);
    }
}
