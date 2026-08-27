package io.github.apat1ya.monitor.dto.member;

import io.github.apat1ya.monitor.entity.member.Role;
import jakarta.validation.constraints.Email;

public record MemberRequestDto(
        @Email
        String email,
        Role role
) {
}
