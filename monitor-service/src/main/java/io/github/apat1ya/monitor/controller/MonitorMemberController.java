package io.github.apat1ya.monitor.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import io.github.apat1ya.monitor.dto.member.MemberRequestDto;
import io.github.apat1ya.monitor.dto.member.MemberResponseDto;
import io.github.apat1ya.monitor.service.MonitorMemberService;
import org.apache.coyote.BadRequestException;
import org.springframework.web.bind.annotation.*;

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
