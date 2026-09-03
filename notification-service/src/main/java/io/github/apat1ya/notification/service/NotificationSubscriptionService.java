package io.github.apat1ya.notification.service;

import event.monitor.member.MemberChangeType;
import event.monitor.member.MonitorMemberChangedEvent;
import io.github.apat1ya.notification.entity.NotificationSubscription;
import io.github.apat1ya.notification.exception.SubscriptionNotFound;
import io.github.apat1ya.notification.repository.NotificationSubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationSubscriptionService {
    private final NotificationSubscriptionRepository notificationSubscriptionRepository;

    public void handle(MonitorMemberChangedEvent event) {
        if (event.changeType() == MemberChangeType.ADDED) {
            addedMember(event);
        } else if (event.changeType() == MemberChangeType.REMOVED) {
            removedMember(event);
        } else {
            throw new IllegalArgumentException("Unsupported change type: " + event.changeType());
        }
    }

    private void addedMember(MonitorMemberChangedEvent monitorMemberChangedEvent) {
        NotificationSubscription subscription = new NotificationSubscription();
        subscription.setUserId(monitorMemberChangedEvent.userId());
        subscription.setMonitorId(monitorMemberChangedEvent.monitorId());
        notificationSubscriptionRepository.save(subscription);
    }


    private void removedMember(MonitorMemberChangedEvent event) {
        NotificationSubscription subscription =
                notificationSubscriptionRepository.findByUserIdAndMonitorId(event.userId(), event.monitorId())
                .orElseThrow(() -> new SubscriptionNotFound("Subscription not found by user id & monitor id"));
        notificationSubscriptionRepository.delete(subscription);
    }
}
