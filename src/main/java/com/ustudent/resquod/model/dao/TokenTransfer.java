package com.ustudent.resquod.model.dao;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TokenTransfer {
    private String token;
    @JsonProperty
    private UserData userData;

    public TokenTransfer(String token, UserData userData) {
        this.token=token;
        this.userData = userData;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
