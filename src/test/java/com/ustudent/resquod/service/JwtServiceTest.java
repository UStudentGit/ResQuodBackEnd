package com.ustudent.resquod.service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JwtServiceTest {
    @Test
    void createAndVerifyToken() throws InterruptedException {
        JwtService jwtService = new JwtService();
        String token = jwtService.sign("test@wp.pl", "USER");
        assertNotEquals(jwtService.verify(token), Optional.empty());
        //change expires time before testing!
        Thread.sleep(10000);
        assertThrows(JWTVerificationException.class, () -> {
            jwtService.verify(token);
        });
    }
}