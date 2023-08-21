package com.reddlyne.suggestai.controller.response;

public class UserLoginResponse {

    private String login;

    private String jwt;

    public UserLoginResponse(String login, String jwt) {
        this.login = login;
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public UserLoginResponse(boolean b, String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public String toString() {
        return "UserLoginResponse{" +
                "jwt='" + login + '\'' +
                '}';
    }
}
