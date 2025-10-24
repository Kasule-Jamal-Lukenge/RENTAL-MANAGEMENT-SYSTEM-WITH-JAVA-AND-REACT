package com.example.rems.auth.dto.UserDtos;

import com.example.rems.enums.Gender;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class UpdateUserRequest {
    @NotBlank
    String firstName;
    @NotBlank String lastName;
    @Email @NotBlank String email;
    @NotBlank @Pattern(regexp = "^[A-Za-z0-9\\-]{5,32}$") String nin;
    @NotNull
    Gender gender;
    @NotNull @Past
    LocalDate dateOfBirth;
    @NotBlank @Pattern(regexp = "^[+0-9 ()-]{7,20}$") String phoneNumber;
    @NotBlank String profession;
    String role;
}
