package com.reddlyne.suggestai.exception;

public class RegistirationNotCompleted extends RuntimeException {

    public RegistirationNotCompleted() {
    }

    public RegistirationNotCompleted(String message) {
        super(message);
    }

    public RegistirationNotCompleted(String message, Throwable cause) {
        super(message, cause);
    }
}
