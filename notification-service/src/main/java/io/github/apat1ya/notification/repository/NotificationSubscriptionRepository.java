package io.github.apat1ya.notification.repository;

import io.github.apat1ya.notification.entity.NotificationSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationSubscriptionRepository extends JpaRepository<NotificationSubscription, Long> {
    Optional<NotificationSubscription> findByUserIdAndMonitorId(Long aLong, Long monitorId);

    @Query("""
            select u.telegramChatId
            from NotificationSubscription s
            join NotificationUser u on u.userId = s.userId
            where s.monitorId = :monitorId
              and u.telegramChatId is not null
            """)
    List<Long> findTelegramChatIdsByMonitorId(Long monitorId);
}
