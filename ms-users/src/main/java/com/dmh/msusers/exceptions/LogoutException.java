package com.dmh.msusers.exceptions;

public class LogoutException extends RuntimeException {
    public LogoutException() {
        super();
    }

    public LogoutException(String message) {
        super(message);
    }

    public LogoutException(String message, Throwable cause) {
        super(message, cause);
    }
}
