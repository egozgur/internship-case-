package com.reddlyne.suggestai.controller.response;

public class UserRegisterResponse {

    private String login;

    private eRegistrationStatus registrationStatus;

    public UserRegisterResponse() {
    }

    public UserRegisterResponse(String _login, eRegistrationStatus _registrationStatus) {
        this.login = _login;
        this.registrationStatus = _registrationStatus;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public eRegistrationStatus getRegistrationStatus() {
        return registrationStatus;
    }

    public void setRegistrationStatus(eRegistrationStatus registrationStatus) {
        this.registrationStatus = registrationStatus;
    }
}
