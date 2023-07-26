package com.reddlyne.suggestai.controller.request;

public class SuggestRequest {

    private String text;

    public SuggestRequest() {
    }

    public SuggestRequest(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
