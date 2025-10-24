package com.example.rems.auth.dto.UserDtos;

import com.example.rems.enums.Gender;

import java.time.LocalDate;
import java.util.Set;

public class UserResponse {
    Long id;
    String firstName;
    String lastName;
    String email;
    String nin;
    Gender gender;
    LocalDate dateOfBirth;
    String phoneNumber;
    String profession;
    Set<String> roles;
}
