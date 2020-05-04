package com.ustudent.resquod.model.dao;

public class UserData {
    private String email;
    private String role;
    private String name;
    private String surname;

    public UserData(String email, String role, String name, String surname) {
        this.email = email;
        this.role = role;
        this.name = name;
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
