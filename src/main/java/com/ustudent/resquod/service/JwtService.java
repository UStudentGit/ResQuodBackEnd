package com.ustudent.resquod.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Random;

@Service
public class JwtService {
    private Algorithm algorithm;
    private JWTVerifier verifier;


    public JwtService() {
        byte[] array = new byte[7];
        new Random().nextBytes(array);
        String secret = new String(array, StandardCharsets.UTF_8);
        algorithm = Algorithm.HMAC256(secret);
        verifier = JWT.require(algorithm).build();
        System.out.println("test!");
    }

    public String sign(String email, String role) {
        return JWT.create()
                .withClaim("email", email)
                .withClaim("role", role)
                .withExpiresAt(Date.from(ZonedDateTime.now().plusDays(7).toInstant()))
                .sign(algorithm);
    }

    public DecodedJWT verify(String token) {
        try {
            DecodedJWT decodedJWT = verifier.verify(token);
            System.out.println(decodedJWT.getClaim("role").asString());
            return decodedJWT;
        } catch (JWTVerificationException e) {
            throw new JWTVerificationException("Invalid token");
        }
    }

}
