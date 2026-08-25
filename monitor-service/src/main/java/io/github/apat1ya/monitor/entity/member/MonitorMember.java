package io.github.apat1ya.monitor.entity.member;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "monitor_members")
@Getter
@Setter
public class MonitorMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long monitorId;
    @Enumerated(EnumType.STRING)
    private Role role;
}
