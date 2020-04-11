package com.ustudent.resquod.controller;

import com.ustudent.resquod.exception.EmailExistException;
import com.ustudent.resquod.model.LoginUserData;
import com.ustudent.resquod.model.User;
import com.ustudent.resquod.repository.UserRepository;
import com.ustudent.resquod.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.InputMismatchException;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class AuthorizationController {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;

    @Autowired
    public AuthorizationController(UserRepository userRepository,
                                   PasswordEncoder passwordEncoder,
                                   JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
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
        User user = new User(inputData.getName(),
                inputData.getSurname(),
                inputData.getEmail(),
                hashPassword(inputData.getPassword()));
        return userRepository.save(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginUserData userInput) {
        if (userInput.getEmail() == null || userInput.getPassword() == null ||
                userInput.getEmail().isEmpty() || userInput.getPassword().isEmpty())
            throw new InputMismatchException("Your input is invalid, please try again!");
        Optional<LoginUserData> userData = userRepository.findUserPassword(userInput.getEmail());
        if (userData.isEmpty()) throw new InputMismatchException("Invalid email adress");
        if (!verifyPassword(userInput.getPassword(), userData.get().getPassword()))
            throw new InputMismatchException("Invalid password");
        return jwtService.sign(userData.get().getEmail(), userData.get().getRole());
    }

    private String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }

    private Boolean verifyPassword(String password, String hash) {
        return passwordEncoder.matches(password, hash);
    }

}
