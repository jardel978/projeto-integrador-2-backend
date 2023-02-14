package com.dmh.msusers.exceptions;

public class KeycloakException extends RuntimeException {

    public KeycloakException() {
        super();
    }

    public KeycloakException(String message) {
        super(message);
    }

    public KeycloakException(String message, Throwable cause) {
        super(message, cause);
    }

}
