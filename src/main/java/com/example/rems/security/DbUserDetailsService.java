package com.example.rems.security;

import com.example.rems.auth.UserRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class DbUserDetailsService implements UserDetailsService {
    private final UserRepository users;

    public DbUserDetailsService(UserRepository users) {
        this.users = users;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var u = users.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return org.springframework.security.core.userdetails.User
                .withUsername(u.getEmail())
                .password(u.getPassword())
                .authorities(u.getRoles().toArray(String[]::new))
                .build();
    }
}
