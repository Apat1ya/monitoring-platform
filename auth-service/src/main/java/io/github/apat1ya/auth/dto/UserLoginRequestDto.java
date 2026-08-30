package io.github.apat1ya.auth.dto;

public record UserLoginRequestDto(
        String email,
        String password
) {
}
