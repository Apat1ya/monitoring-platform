package io.github.apat1ya.auth.service;

import io.github.apat1ya.auth.dto.UserLoginRequestDto;
import io.github.apat1ya.auth.dto.UserResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    public UserResponseDto authenticate(@Valid UserLoginRequestDto requestDto) {
    }
}
