package io.github.apat1ya.auth.service;

import io.github.apat1ya.auth.entity.UserEntity;
import io.github.apat1ya.auth.exception.UserNotFoundException;
import io.github.apat1ya.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Long findUserIdByEmail(String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("user not found by email"));
        return user.getId();
    }
}
