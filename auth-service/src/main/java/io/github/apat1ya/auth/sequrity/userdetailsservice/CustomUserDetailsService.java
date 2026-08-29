package io.github.apat1ya.auth.sequrity.userdetailsservice;

import io.github.apat1ya.auth.entity.UserEntity;
import io.github.apat1ya.auth.repository.UserRepository;
import io.github.apat1ya.auth.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found by email"));
        return User.builder()
                .username(userEntity.getEmail())
                .password(userEntity.getPassword())
                .build();
    }
}
