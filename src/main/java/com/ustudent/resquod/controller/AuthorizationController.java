package com.ustudent.resquod.controller;

import com.ustudent.resquod.exception.EmailExistException;
import com.ustudent.resquod.exception.InvalidInputException;
import com.ustudent.resquod.exception.InvalidPasswordException;
import com.ustudent.resquod.model.User;
import com.ustudent.resquod.model.dao.LoginUserData;
import com.ustudent.resquod.service.JwtService;
import com.ustudent.resquod.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@Api(value = "User management")
@RequestMapping("/user")
public class AuthorizationController {

    private final JwtService jwtService;
    private final UserService userService;

    @Autowired
    public AuthorizationController(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @ApiOperation(value = "Create new user")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully created!"),
            @ApiResponse(code = 400, message = "\"Invalid input!\" or \"Email already taken!\""),
            @ApiResponse(code = 500, message = "User cannot be registered!")})
    @PostMapping("/register")
    public String register(
            @ApiParam(value = "Required email, name, surname, password", required = true)
            @RequestBody User inputData) {
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

    @ApiOperation(value = "Login as user")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Token"),
            @ApiResponse(code = 400, message = "\"Invalid input!\" or \"Email don't exist!\" or \"Invalid password!\""),
            @ApiResponse(code = 500, message = "Server Error!")})
    @PostMapping("/login")
    public String login(
            @ApiParam(value = "Required email, password", required = true)
            @RequestBody LoginUserData userInput) {
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

    @ApiOperation(value = "Get current user")
    @ApiResponses(value = {@ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Server Error!")})
    @GetMapping("/user")
    public User getUser() {
        try {
            String user = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
            return userService.getUser(user);
        } catch (EmailExistException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request");
        }
    }

    @ApiOperation(value = "Change user data")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully updated!"),
            @ApiResponse(code = 400, message = "\"Invalid input!\" or \"Invalid password!\""),
            @ApiResponse(code = 500, message = "Server Error!")})
    @PutMapping("userData")
    public String changeUserData(
            @ApiParam(value = "Required email, name, surname, password", required = true)
            @RequestBody User userInput) {
        try {
            //TODO dodać sprawdzanie czy podany user jest teraz zalogowany!
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
