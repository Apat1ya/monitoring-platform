package io.github.apat1ya.auth.dto;

public record UserRegistrationRequestDto(
        String firstName,
        String secondName,
        String email,
        String password,
        String repeatPassword
) {
}
