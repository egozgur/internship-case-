package com.reddlyne.suggestai.controller.response;

public class SuggestResponse {

    private String responseText;

    public SuggestResponse() {
    }

    public SuggestResponse(String responseText) {
        this.responseText = responseText;
    }

    public String getResponseText() {
        return responseText;
    }

    public void setResponseText(String responseText) {
        this.responseText = responseText;
    }
}
