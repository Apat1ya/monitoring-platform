package com.apipulse.monitor.dto.member;

import jakarta.validation.constraints.Email;
import com.apipulse.monitor.entity.member.Role;

public record MemberRequestDto(
        @Email
        String email,
        Role role
) {
}
