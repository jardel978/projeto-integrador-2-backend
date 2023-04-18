package com.dmh.msaccounts.exception;

public class UserFeignClientException extends RuntimeException {
    public UserFeignClientException() {
        super();
    }

    public UserFeignClientException(String message) {
        super(message);
    }

    public UserFeignClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
