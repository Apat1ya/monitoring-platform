package io.github.apat1ya.notification.service;

import event.auth.UserRegisteredEvent;
import io.github.apat1ya.notification.entity.NotificationUser;
import io.github.apat1ya.notification.repository.NotificationUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationUserService {
    private final NotificationUserRepository userRepository;

    public void addNewUser(UserRegisteredEvent userRegisteredEvent) {
        NotificationUser user = new NotificationUser();
        user.setUserId(userRegisteredEvent.userId());
        user.setEmail(userRegisteredEvent.email());
        userRepository.save(user);
    }
}
