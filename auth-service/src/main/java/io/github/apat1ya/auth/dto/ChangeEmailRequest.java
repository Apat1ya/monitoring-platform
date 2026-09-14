package io.github.apat1ya.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ChangeEmailRequest(
        @Email
        @NotBlank
        String email,
        @NotBlank
        String password
) {
}
