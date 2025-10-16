package com.example.rems.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.Set;

import static org.springframework.security.config.Elements.JWT;

@Service
public class JwtService {
    private final Algorithm algorithm;
    private final String issuer;
    private final long expiryMinutes;

    public JwtService(
            @Value("${app.security.jwt.secret:}") String secret,
            @Value("${app.security.jwt.issuer}") String issuer,
            @Value("${app.security.jwt.expiryMinutes}") long expiryMinutes
    ) {
        if (secret == null || secret.isBlank()) {
            byte[] bytes = new byte[64];
            new SecureRandom().nextBytes(bytes);
            secret = Base64.getEncoder().encodeToString(bytes);
            System.out.println("⚠️  Generated random JWT secret (dev only): " + secret);
        }
        this.algorithm = Algorithm.HMAC256(secret);
        this.issuer = issuer;
        this.expiryMinutes = expiryMinutes;
    }

    public String generate(String email, Set<String> roles) {
        Instant now = Instant.now();
        Instant exp = now.plusSeconds(expiryMinutes * 60);
        return JWT.create()
                .withIssuer(issuer)
                .withSubject(email)
                .withArrayClaim("roles", roles.toArray(String[]::new))
                .withIssuedAt(Date.from(now))
                .withExpiresAt(Date.from(exp))
                .sign(algorithm);
    }

    public String verifyAndGetSubject(String token) {
        return JWT.require(algorithm).withIssuer(issuer).build().verify(token).getSubject();
    }
}
