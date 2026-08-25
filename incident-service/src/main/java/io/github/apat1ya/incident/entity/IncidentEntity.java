package io.github.apat1ya.incident.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter
@Setter
public class IncidentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long monitorId;
    @Column(nullable = false)
    private Long endpointId;
    @Column(nullable = false)
    private String checkedUrl;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(nullable = false)
    private Integer statusCode;
    @Column(nullable = false)
    private Instant startedAt;
    @Column(nullable = false)
    private Instant resolvedAt;
    @Column(length = 1000)
    private String errorMessage;
}
