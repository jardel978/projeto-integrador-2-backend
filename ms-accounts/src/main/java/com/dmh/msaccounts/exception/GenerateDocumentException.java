package com.dmh.msaccounts.exception;

public class GenerateDocumentException extends RuntimeException {
    public GenerateDocumentException() {
        super();
    }

    public GenerateDocumentException(String message) {
        super(message);
    }

    public GenerateDocumentException(String message, Throwable cause) {
        super(message, cause);
    }
}
