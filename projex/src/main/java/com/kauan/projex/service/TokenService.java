package com.kauan.projex.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

@Service
public class TokenService {
    
    @Value("${api.security.token.secret:projext-secret-123}")
    private String secret;

    private static final String ISSUER = "projex-auth-api";

    public String gerarToken(String clientId){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
            .withIssuer(ISSUER)
            .withSubject(clientId)
            .withExpiresAt(gerarDataExpiracao())
            .sign(algorithm);
        }catch(JWTCreationException exception){
            throw new RuntimeException("Erro ao gerar o Token JWT", exception);
        }

    }
    public String validarToken(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
            .withIssuer(ISSUER)
            .build()
            .verify(token)
            .getSubject();
        }catch(JWTVerificationException exception){
            return null;
        }
    }

    public Instant gerarDataExpiracao(){
        return LocalDateTime.now().plusHours(2).toInstant((ZoneOffset.of("-03:00")));
    }
}
