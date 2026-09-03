package io.github.apat1ya.auth.service;

import event.auth.UserRegisteredEvent;
import io.github.apat1ya.auth.dto.AuthResponseDto;
import io.github.apat1ya.auth.dto.UserLoginRequestDto;
import io.github.apat1ya.auth.dto.UserRegistrationRequestDto;
import io.github.apat1ya.auth.entity.UserEntity;
import io.github.apat1ya.auth.exception.RegistrationException;
import io.github.apat1ya.auth.messaging.producer.UserRegisteredProducer;
import io.github.apat1ya.auth.repository.UserRepository;
import io.github.apat1ya.auth.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRegisteredProducer registeredProducer;

    public void registration(UserRegistrationRequestDto requestDto) {
        if (userRepository.existsByEmail(requestDto.email())) {
            throw new RegistrationException("User with email" + requestDto.email()
                    + "already exist");
        }
        UserEntity user = new UserEntity();
        user.setEmail(requestDto.email());
        user.setFirstName(requestDto.firstName());
        user.setSecondName(requestDto.secondName());
        user.setPassword(passwordEncoder.encode(requestDto.password()));
        userRepository.save(user);
        registeredProducer.send(new UserRegisteredEvent(
                user.getId(),
                user.getEmail()
        ));
    }

    public AuthResponseDto login(UserLoginRequestDto requestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestDto.email(),
                        requestDto.password()
                )
        );
        String token = jwtService.generateToken(authentication);
        return new AuthResponseDto(token);
    }
}
