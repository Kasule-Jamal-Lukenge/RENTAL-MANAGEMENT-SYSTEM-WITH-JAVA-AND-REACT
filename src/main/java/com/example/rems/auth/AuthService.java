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
        if (users.existsByEmail(req.email())) {
            throw new IllegalArgumentException("Email already registered");
        }
        var user = User.builder()
                .email(req.email())
                .password(encoder.encode(req.password()))
                .roles(Set.of("ROLE_USER"))
                .build();
        users.save(user);
    }

    public AuthResponse login(LoginRequest req) {
        authMgr.authenticate(new UsernamePasswordAuthenticationToken(req.email(), req.password()));
        var roles = users.findByEmail(req.email()).orElseThrow().getRoles();
        String token = jwt.generate(req.email(), roles);
        return new AuthResponse(token, "Bearer");
    }
}
