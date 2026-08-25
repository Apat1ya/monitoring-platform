package io.github.apat1ya.monitor.service;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import io.github.apat1ya.monitor.client.AuthClient;
import io.github.apat1ya.monitor.dto.member.MemberRequestDto;
import io.github.apat1ya.monitor.dto.member.MemberResponseDto;
import io.github.apat1ya.monitor.entity.member.MonitorMember;
import io.github.apat1ya.monitor.entity.member.Role;
import io.github.apat1ya.monitor.exception.UserNotFoundException;
import io.github.apat1ya.monitor.mapper.MonitorMemberMapper;
import io.github.apat1ya.monitor.repository.MemberMonitorRepository;
import io.github.apat1ya.monitor.service.support.AccessChecker;
import io.github.apat1ya.monitor.service.support.CurrentUserProvider;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

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
