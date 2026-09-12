package io.github.apat1ya.auth.dto;

import jakarta.validation.constraints.Email;

public record EmailChangeData(
        Long userId,
        @Email
        String email
) {
}
