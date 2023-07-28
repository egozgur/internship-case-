package com.reddlyne.suggestai.controller.response;

public class UserLoginResponse {

    private long userId;

    public UserLoginResponse() {
    }

    public UserLoginResponse(long userId) {
        this.userId = userId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UserLoginResponse{" +
                "jwt='" + userId + '\'' +
                '}';
    }
}
