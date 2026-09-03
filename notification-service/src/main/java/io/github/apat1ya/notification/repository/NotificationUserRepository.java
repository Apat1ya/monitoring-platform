package io.github.apat1ya.notification.repository;

import io.github.apat1ya.notification.entity.NotificationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotificationUserRepository extends JpaRepository<NotificationUser, Long> {
    Optional<NotificationUser> findByUserId(Long userId);
}
