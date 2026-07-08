package com.apipulse.monitor.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.apipulse.monitor.client.AuthClient;
import com.apipulse.monitor.dto.member.MemberRequestDto;
import com.apipulse.monitor.dto.member.MemberResponseDto;
import com.apipulse.monitor.entity.member.MonitorMember;
import com.apipulse.monitor.entity.member.Role;
import com.apipulse.monitor.exception.UserNotFoundException;
import com.apipulse.monitor.mapper.MonitorMemberMapper;
import com.apipulse.monitor.repository.MemberMonitorRepository;
import com.apipulse.monitor.service.support.AccessChecker;
import com.apipulse.monitor.service.support.CurrentUserProvider;
import feign.FeignException;
import org.apache.coyote.BadRequestException;

@Service
@RequiredArgsConstructor
public class MonitorMemberService {
    private final MemberMonitorRepository memberMonitorRepository;
    private final CurrentUserProvider userProvider;
    private final AccessChecker accessChecker;
    private final AuthClient authClient;
    private final MonitorMemberMapper mapper;

    public MemberResponseDto addUser(Long monitorId, MemberRequestDto requestDto) {
        accessChecker.checkAccessOwner(userProvider.getCurrentUserId(),monitorId);
        Long addedUserId;
        try {
            addedUserId = authClient.checkUser(requestDto.email());
        } catch (FeignException.NotFound ex) {
            throw new UserNotFoundException("User not found by email");
        }
        MonitorMember member = new MonitorMember();
        member.setUserId(addedUserId);
        member.setMonitorId(monitorId);
        member.setRole(requestDto.role());
        memberMonitorRepository.save(member);
        return mapper.toResponse(member);
    }

    public MemberResponseDto edit(Long monitorId, MemberRequestDto requestDto)
            throws BadRequestException {
        accessChecker.checkAccessOwner(userProvider.getCurrentUserId(), monitorId);
        Long targetUserId;
        try {
            targetUserId = authClient.checkUser(requestDto.email());
        } catch (FeignException.NotFound ex) {
            throw new UserNotFoundException("User not found by email");
        }
        MonitorMember member = memberMonitorRepository
                .findByUserIdAndMonitorId(targetUserId, monitorId)
                .orElseThrow(() -> new UserNotFoundException("User is not member of this monitor"));
        if (requestDto.role() == Role.OWNER) {
            throw new BadRequestException("Cannot assign OWNER role");
        }
        member.setRole(requestDto.role());
        memberMonitorRepository.save(member);
        return mapper.toResponse(member);
    }

    public void delete(Long monitorId, MemberRequestDto requestDto) {
        accessChecker.checkAccessOwner(userProvider.getCurrentUserId(), monitorId);
        Long targetUserId;
        try {
            targetUserId = authClient.checkUser(requestDto.email());
        } catch (FeignException.NotFound ex) {
            throw new UserNotFoundException("User not found by email");
        }
        MonitorMember member = memberMonitorRepository
                .findByUserIdAndMonitorId(targetUserId, monitorId)
                .orElseThrow(() -> new UserNotFoundException("User is not member of this monitor"));
        memberMonitorRepository.delete(member);
    }
}
