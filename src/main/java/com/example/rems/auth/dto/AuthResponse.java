package com.example.rems.auth.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AuthResponse {
    String accessToken;
    String tokenType;
}
