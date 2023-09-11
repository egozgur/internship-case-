package com.reddlyne.suggestai.controller.request;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SuggestRequest {

    @JsonAlias({"message", "text"})
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

    public String getMessage() { return text; }

}