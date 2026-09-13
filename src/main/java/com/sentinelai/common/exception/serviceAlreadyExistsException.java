package com.sentinelai.common.exception;

public class serviceAlreadyExistsException extends RuntimeException {

    public serviceAlreadyExistsException(String message) {
        super(message);
    }
}