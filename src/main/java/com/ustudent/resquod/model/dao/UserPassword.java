package com.ustudent.resquod.model.dao;

public class UserPassword {

    private String oldPassword;
    private String newPassword;

    public UserPassword(String oldPassword, String newPassword){
        this.newPassword=newPassword;
        this.oldPassword=oldPassword;
    };

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

}
