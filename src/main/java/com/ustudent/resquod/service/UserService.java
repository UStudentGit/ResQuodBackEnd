package com.ustudent.resquod.service;

import com.ustudent.resquod.exception.EmailExistException;
import com.ustudent.resquod.exception.InvalidInputException;
import com.ustudent.resquod.exception.InvalidPasswordException;
import com.ustudent.resquod.model.User;
import com.ustudent.resquod.model.dao.LoginUserData;
import com.ustudent.resquod.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Configurable(preConstruction = true, autowire = Autowire.BY_NAME)
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void validateLoginData(LoginUserData userInput) {
        if (userInput.getEmail() == null || userInput.getPassword() == null ||
                userInput.getEmail().isEmpty() || userInput.getPassword().isEmpty())
            throw new InvalidInputException();
    }

    public LoginUserData getUserDataIfExist(String email) throws EmailExistException {
        return userRepository.findUserPassword(email).orElseThrow(EmailExistException::new);
    }

    public void checkIfMailExist(String email) throws EmailExistException {
        if (userRepository.findByEmail(email).isPresent())
            throw new EmailExistException();
    }

    public void validateRegistrationData(User inputData) throws InvalidInputException {
        System.out.println("aaa");
        if (inputData.getEmail() == null || inputData.getEmail().isEmpty() ||
                inputData.getName() == null || inputData.getName().isEmpty() ||
                inputData.getSurname() == null || inputData.getSurname().isEmpty() ||
                inputData.getPassword().length() < 6) throw new InvalidInputException();
        System.out.println("dzial");
    }

    public void addUser(User inputData) throws RuntimeException {
        userRepository.save(new User(inputData.getName(),
                inputData.getSurname(),
                inputData.getEmail(),
                hashPassword(inputData.getPassword())));
    }

    private String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }

    public Boolean verifyPassword(String password, String hash) throws InvalidPasswordException {
        if (!passwordEncoder.matches(password, hash)) throw new InvalidPasswordException();
        return true;
    }

}
