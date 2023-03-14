package com.dmh.msaccounts.exception;

public class InvalidFieldException extends RuntimeException {


    public InvalidFieldException() {
    }

    public InvalidFieldException(String message) {
        super(message);
    }

    public InvalidFieldException(String message, Throwable cause) {
        super(message, cause);
    }

}
