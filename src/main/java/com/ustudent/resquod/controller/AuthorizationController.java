package com.ustudent.resquod.controller;

import com.ustudent.resquod.exception.EmailExistException;
import com.ustudent.resquod.exception.InvalidInputException;
import com.ustudent.resquod.exception.InvalidPasswordException;
import com.ustudent.resquod.model.User;
import com.ustudent.resquod.model.dao.LoginUserData;
import com.ustudent.resquod.service.JwtService;
import com.ustudent.resquod.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/user")
public class AuthorizationController {

    private final JwtService jwtService;
    private final UserService userService;

    @Autowired
    public AuthorizationController(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }


    @PostMapping("/register")
    public Object register(@RequestBody User inputData) {
        try {
            userService.checkIfMailExist(inputData.getEmail());
        } catch (EmailExistException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already taken!", ex);
        }
        try {
            userService.validateRegistrationData(inputData);
        } catch (InvalidInputException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid input!", ex);
        }
        try {
            userService.addUser(inputData);
        } catch (RuntimeException ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "User cannot be registered!");
        }
        return new ResponseStatusException(HttpStatus.OK, "Successfully created!");

    }

    @PostMapping("/login")
    public String login(@RequestBody LoginUserData userInput) {
        LoginUserData userData;
        try {
            userService.validateLoginData(userInput);
        } catch (InvalidInputException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid input!");
        }
        try {
            userData = userService.getUserDataIfExist(userInput.getEmail());
        } catch (EmailExistException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email don't exist!");
        }
        try {
            userService.verifyPassword(userInput.getPassword(), userData.getPassword());
        } catch (InvalidPasswordException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid password!");
        }
        return jwtService.sign(userData.getEmail(), userData.getRole());
    }


}
