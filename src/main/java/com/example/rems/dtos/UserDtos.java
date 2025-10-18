package com.example.rems.dtos;

import com.example.rems.enums.Gender;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class UserDtos {

    public record CreateUserRequest(
            @NotBlank
            String firstName,
            @NotBlank
            String lastName,
            @Email @NotBlank
            String email,
            @NotBlank @Pattern(regexp = "^[A-Za-z0-9\\-]{5,32}$",
                    message = "NIN must be 5-32 chars, letters/digits/hyphen")
            String nin,
            @NotNull
            Gender gender,
            @NotNull @Past
            LocalDate dateOfBirth,
            @NotBlank @Pattern(regexp = "^[+0-9 ()-]{7,20}$", message = "Invalid phone number")
            String phoneNumber,
            @NotBlank
            String profession,
            @NotBlank @Size(min = 8, message = "Password must be at least 8 characters") String password,
            // optional: set roles on create; default to ROLE_USER if null
            String role // e.g., "ROLE_ADMIN" or "ROLE_USER"
    ) {}

    public record UpdateUserRequest(
            @NotBlank
            String firstName,
            @NotBlank
            String lastName,
            @Email @NotBlank
            String email,
            @NotBlank @Pattern(regexp = "^[A-Za-z0-9\\-]{5,32}$")
            String nin,
            @NotNull
            Gender gender,
            @NotNull @Past
            LocalDate dateOfBirth,
            @NotBlank @Pattern(regexp = "^[+0-9 ()-]{7,20}$")
            String phoneNumber,
            @NotBlank
            String profession,
            String role // optional; omit or null to keep existing
    ) {}

    public record UserResponse(
            Long id,
            String firstName,
            String lastName,
            String email,
            String nin,
            Gender gender,
            LocalDate dateOfBirth,
            String phoneNumber,
            String profession,
            java.util.Set<String> roles
    ) {}
}
