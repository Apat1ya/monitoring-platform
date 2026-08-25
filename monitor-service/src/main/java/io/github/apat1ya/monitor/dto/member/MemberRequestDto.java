package io.github.apat1ya.monitor.dto.member;

import jakarta.validation.constraints.Email;
import io.github.apat1ya.monitor.entity.member.Role;

public record MemberRequestDto(
        @Email
        String email,
        Role role
) {
}
