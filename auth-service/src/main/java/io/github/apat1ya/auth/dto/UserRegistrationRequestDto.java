package io.github.apat1ya.auth.dto;

import io.github.apat1ya.auth.validation.annotation.PasswordMatches;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@PasswordMatches
public record UserRegistrationRequestDto(
        @NotBlank
        @Size(max = 25)
        String firstName,
        @Size(max = 25)
        String secondName,
        @Email(message = "Field email can`t be empty")
        String email,
        @NotBlank(message = "Field password can`t be empty")
        @Size(min = 8, max = 25)
        String password,
        @NotBlank
        String repeatPassword
) {
}
