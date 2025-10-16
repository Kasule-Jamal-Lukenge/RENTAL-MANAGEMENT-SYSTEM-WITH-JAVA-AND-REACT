

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(
        @Email
        @NotBlank
        String email,
        @NotBlank
        String password
) {}

public record LoginRequest(
        @Email
        @NotBlank
        String email,
        @NotBlank
        String password
) {}

public record AuthResponse(
        String accessToken,
        String tokenType
) {}


