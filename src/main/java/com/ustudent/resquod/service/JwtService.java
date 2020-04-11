package com.ustudent.resquod.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.time.ZonedDateTime;
import java.util.Date;

public class JwtService {
    private Algorithm algorithm;
    private JWTVerifier verifier;

    public JwtService(String secret) {
        algorithm = Algorithm.HMAC256(secret);
        verifier = JWT.require(algorithm).build();
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
            return verifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new JWTVerificationException("Invalid token");
        }
    }

}
