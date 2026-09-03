package io.github.apat1ya.auth.controller;

import io.github.apat1ya.auth.dto.AuthResponseDto;
import io.github.apat1ya.auth.dto.UserLoginRequestDto;
import io.github.apat1ya.auth.dto.UserRegistrationRequestDto;
import io.github.apat1ya.auth.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/registration")
    public ResponseEntity<Void> registration(@RequestBody UserRegistrationRequestDto requestDto) {
        authenticationService.registration(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public AuthResponseDto login(@RequestBody @Valid UserLoginRequestDto requestDto) {
        return authenticationService.login(requestDto);
    }
}
