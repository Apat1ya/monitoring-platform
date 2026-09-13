package io.github.apat1ya.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record ConfirmEmailChangeRequest(
        @NotBlank
        String token
) {
}
