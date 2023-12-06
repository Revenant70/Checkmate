package com.todo.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

public class JWTUtil {

    Algorithm algorithm =Algorithm.HMAC256("CheckMate");

    JWTVerifier verifier = JWT.require(algorithm)
            .withIssuer("CheckMate")
            .build();

    String jwtToken = JWT.create()
            .withIssuer("CheckMate")
            .withSubject("CheckMate Details")
            .withClaim("userid", "1234")
            .withIssuedAt(new Date())
            .withExpiresAt(new Date(System.currentTimeMillis() + 5000L))
            .withJWTId(UUID.randomUUID().toString())
            .withNotBefore(new Date(System.currentTimeMillis() + 1000L))
            .sign(algorithm);

    @Bean
    public String decode(){
        try {
            DecodedJWT decodedJWT = verifier.verify(jwtToken);
        } catch (JWTVerificationException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

}
