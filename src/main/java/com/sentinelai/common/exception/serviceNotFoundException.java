package com.sentinelai.common.exception;

public class serviceNotFoundException extends RuntimeException {

    public serviceNotFoundException(String message) {
        super(message);
    }
}