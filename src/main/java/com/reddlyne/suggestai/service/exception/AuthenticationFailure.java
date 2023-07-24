package com.reddlyne.suggestai.service.exception;

public class AuthenticationFailure extends RuntimeException {

    public AuthenticationFailure() {
    }

    public AuthenticationFailure(String message) {
        super(message);
    }

    public AuthenticationFailure(String message, Throwable cause) {
        super(message, cause);
    }
}
