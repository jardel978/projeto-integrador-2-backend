package com.dmh.msaccounts.exception;

public class DataAlreadyExistsException extends RuntimeException{

    public DataAlreadyExistsException() {
        super();
    }

    public DataAlreadyExistsException(String message) {
        super(message);
    }

    public DataAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
