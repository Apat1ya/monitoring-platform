package io.github.apat1ya.auth.controller;

import io.github.apat1ya.auth.dto.UserLoginRequestDto;
import io.github.apat1ya.auth.dto.UserRegistrationRequestDto;
import io.github.apat1ya.auth.dto.UserResponseDto;
import io.github.apat1ya.auth.service.AuthenticationService;
import io.github.apat1ya.auth.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/registration")
    public UserResponseDto registration(@RequestBody UserRegistrationRequestDto requestDto) {
        return userService.registration(requestDto);
    }

    @PostMapping("/login")
    public UserResponseDto login(@RequestBody @Valid UserLoginRequestDto requestDto) {
        return authenticationService.authenticate(requestDto);
    }
}
