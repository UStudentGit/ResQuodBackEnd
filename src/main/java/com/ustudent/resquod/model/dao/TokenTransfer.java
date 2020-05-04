package com.ustudent.resquod.model.dao;

public class TokenTransfer {
    private String token;

    public TokenTransfer(String token) {
        this.token=token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
