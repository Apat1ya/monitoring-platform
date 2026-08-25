package io.github.apat1ya.monitor.dto.member;

import io.github.apat1ya.monitor.entity.member.Role;

public record MemberResponseDto(
        String email,
        Role role
) {
}
