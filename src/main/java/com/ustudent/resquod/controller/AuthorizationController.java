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
import org.springframework.web.bind.annotation.*;
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
    public String register(@RequestBody User inputData) {
        try {
            userService.checkIfMailExist(inputData.getEmail());
            userService.validateRegistrationData(inputData);
            userService.addUser(inputData);
        } catch (EmailExistException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already taken!", ex);
        } catch (InvalidInputException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid input!", ex);
        } catch (RuntimeException ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "User cannot be registered!");
        }
        return "Successfully created!";

    }

    @PostMapping("/login")
    public String login(@RequestBody LoginUserData userInput) {
        LoginUserData userData;
        try {
            userService.validateLoginData(userInput);
            userData = userService.getUserDataIfExist(userInput.getEmail());
            userService.verifyPassword(userInput.getPassword(), userData.getPassword());
        } catch (InvalidInputException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid input!");
        } catch (EmailExistException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email don't exist!");
        } catch (InvalidPasswordException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid password!");
        }
        return jwtService.sign(userData.getEmail(), userData.getRole());
    }

    @PutMapping("userData")
    public String changeUserData(@RequestBody User userInput) {
        try {
            userService.validateUserData(userInput);
            userService.updateUserData(userInput);
        } catch (InvalidInputException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid input!");
        } catch (InvalidPasswordException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid password!");
        }
        return "Successfully updated!";
    }


}
