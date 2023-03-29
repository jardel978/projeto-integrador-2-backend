package com.dmh.msusers.exceptions;

public class CreateAccountException extends RuntimeException {
    public CreateAccountException() {
        super();
    }

    public CreateAccountException(String message) {
        super(message);
    }

    public CreateAccountException(String message, Throwable cause) {
        super(message, cause);
    }
}
