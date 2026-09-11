package io.github.apat1ya.auth.service;

import io.github.apat1ya.auth.entity.UserEntity;
import io.github.apat1ya.auth.exception.UserNotFoundException;
import io.github.apat1ya.auth.repository.UserRepository;
import io.github.apat1ya.auth.service.support.CurrentUserProvider;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final CurrentUserProvider currentUser;

    public Long findUserIdByEmail(String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("user not found by email"));
        return user.getId();
    }

    public String requestEmailChange(String newEmail) {
        UserEntity user = userRepository.findById(currentUser.getCurrentUserId())
                .orElseThrow(() -> new UserNotFoundException ("user not found"));

    }
}
