package io.github.apat1ya.monitor.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import io.github.apat1ya.monitor.entity.member.MonitorMember;

@Entity
@Getter
@Setter
@Table(name = "monitor")
public class MonitorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long userId;
    private MonitorMember member;
    @Column(nullable = false)
    private String target;
    private String description;
    @Column(nullable = false)
    private boolean active;
}
