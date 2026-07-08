package com.apipulse.monitor.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import com.apipulse.monitor.entity.member.MonitorMember;

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
