package com.test.rovers.exception;

public class MarsException extends RuntimeException {

    public MarsException(String message, Throwable cause) {
        super(message, cause);
    }

    public MarsException(String message) {
        super(message);
    }

    public MarsException(Throwable cause) {
        super(cause);
    }
}
