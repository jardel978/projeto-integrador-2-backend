package com.dmh.msusers.exceptions;

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
