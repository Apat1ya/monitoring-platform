package com.apipulse.monitor.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpMethod;

@Entity
@Getter
@Setter
public class CheckResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long monitorId;
    @Column(nullable = false)
    private Long endpointId;
    @Column(nullable = false, length = 2000)
    private String checkedUrl;
    @Column(nullable = false)
    private boolean success;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private HttpMethod httpMethod;
    private Integer statusCode;
    @Column(nullable = false)
    private Long responseTime;
    @Column(length = 1000)
    private String errorMessage;
    @Column(nullable = false)
    private Long checkedAt;
}
