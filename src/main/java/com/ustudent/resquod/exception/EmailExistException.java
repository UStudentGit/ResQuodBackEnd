package com.ustudent.resquod.exception;

public class EmailExistException extends RuntimeException {
    public EmailExistException(String email) {
        super("Email: " + email + " is already taken");
    }
}
