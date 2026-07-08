package com.apipulse.monitor.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.apipulse.monitor.dto.member.MemberRequestDto;
import com.apipulse.monitor.dto.member.MemberResponseDto;
import com.apipulse.monitor.service.MonitorMemberService;
import org.apache.coyote.BadRequestException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/monitors/{monitorId}/members")
public class MonitorMemberController {
    private final MonitorMemberService monitorMemberService;

    @PostMapping("/add")
    public MemberResponseDto addUserToMonitor(@PathVariable Long monitorId,
                                              @RequestBody @Valid MemberRequestDto requestDto) {
        return monitorMemberService.addUser(monitorId, requestDto);
    }

    @PatchMapping("/edit")
    public MemberResponseDto editUserAccess(@PathVariable Long monitorId,
                                            @RequestBody @Valid MemberRequestDto requestDto)
            throws BadRequestException {
        return monitorMemberService.edit(monitorId,requestDto);
    }

    @DeleteMapping("/delete")
    public void deleteUserFromMonitor(@PathVariable Long monitorId,
                                      @RequestBody @Valid MemberRequestDto requestDto) {
        monitorMemberService.delete(monitorId, requestDto);
    }
}
