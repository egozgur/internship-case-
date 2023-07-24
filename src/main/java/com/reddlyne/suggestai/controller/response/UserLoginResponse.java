package com.reddlyne.suggestai.controller.response;

public class UserLoginResponse {

    private String jwt;

    public UserLoginResponse() {
    }

    public UserLoginResponse(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    @Override
    public String toString() {
        return "UserLoginResponse{" +
                "jwt='" + jwt + '\'' +
                '}';
    }
}
