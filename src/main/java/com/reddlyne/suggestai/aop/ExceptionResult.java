package com.reddlyne.suggestai.aop;

import org.springframework.http.HttpStatus;

public class ExceptionResult {

    private String description;

    private String message;

    private HttpStatus status;

    public ExceptionResult() {
    }

    public ExceptionResult(String description, String message, HttpStatus status) {
        this.description = description;
        this.message = message;
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
