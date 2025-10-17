package com.example.rems.auth;

import com.example.rems.auth.dto.*;
import com.example.rems.security.JwtService;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthService {
    private final UserRepository users;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authMgr;
    private final JwtService jwt;

    public AuthService(UserRepository users, PasswordEncoder encoder,
                       AuthenticationManager authMgr, JwtService jwt) {
        this.users = users;
        this.encoder = encoder;
        this.authMgr = authMgr;
        this.jwt = jwt;
    }

    public void register(RegisterRequest req) {
        if (users.existsByEmail(req.getEmail())) {
            throw new IllegalArgumentException("Email already registered");
        }
        var user = User.builder()
                .email(req.getEmail())
                .password(encoder.encode(req.getPassword()))
                .roles(Set.of("ROLE_USER"))
                .build();
        users.save(user);
    }

    public AuthResponse login(LoginRequest req) {
        authMgr.authenticate(new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));
        var roles = users.findByEmail(req.getEmail()).orElseThrow().getRoles();
        String token = jwt.generate(req.getEmail(), roles);
        return new AuthResponse(token, "Bearer");
    }
}
