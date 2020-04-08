package com.ustudent.resquod.controller;

import com.ustudent.resquod.exception.EmailExistException;
import com.ustudent.resquod.model.User;
import com.ustudent.resquod.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.InputMismatchException;

@RestController
@RequestMapping("/user")
public class AuthorizationController {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthorizationController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

    }


    @PostMapping("/checkIfMailExist/{email}")
    public Boolean checkIfMailExist(@PathVariable String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @PostMapping("/register")
    public User register(@RequestBody User inputData) throws EmailExistException {
        if (checkIfMailExist(inputData.getEmail())) throw new EmailExistException(inputData.getEmail());
        if (inputData.getEmail() == null ||
                inputData.getName() == null ||
                inputData.getSurname() == null ||
                inputData.getPassword().length() < 6
        ) throw new InputMismatchException("Your input is invalid, please try again!");
        //TODO add nice exceptions
        User user = new User(inputData.getName(), inputData.getSurname(), inputData.getEmail(), passwordEncoder.encode(inputData.getPassword()));
        return userRepository.save(user);
    }


}
