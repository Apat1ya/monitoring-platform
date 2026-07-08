package com.apipulse.monitor.dto.member;

import com.apipulse.monitor.entity.member.Role;

public record MemberResponseDto(
        String email,
        Role role
) {
}
