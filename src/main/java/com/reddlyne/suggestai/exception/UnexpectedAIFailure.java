package com.reddlyne.suggestai.exception;

public class UnexpectedAIFailure extends RuntimeException {

    public UnexpectedAIFailure() {
        super();
    }

    public UnexpectedAIFailure(String message) {
        super(message);
    }

    public UnexpectedAIFailure(String message, Throwable cause) {
        super(message, cause);
    }
}
