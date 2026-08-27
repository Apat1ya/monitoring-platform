package io.github.apat1ya.monitor.entity.member;

import io.github.apat1ya.monitor.entity.MonitorEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class MonitorMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "monitor", nullable = false)
    private MonitorEntity monitor;
    @Enumerated(EnumType.STRING)
    private Role role;
}
