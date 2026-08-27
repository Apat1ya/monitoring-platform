package io.github.apat1ya.monitor.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpMethod;

@Entity
@Getter
@Setter
public class EndpointEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long monitorId;
    private HttpMethod httpMethod;
    private String path;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private String body;
    @Column(nullable = false)
    private boolean active;
    @Column(nullable = false)
    private int expectedStatusCode;
    @Column(nullable = false)
    private int checkIntervalSeconds;
    @Column(nullable = false)
    private int failureThreshold; //TODO в сервисе проверять значения и если null тогда по дефолту ставить значение 3
    @Column(nullable = false)
    private int recoveryThreshold;
    private int failureCounter;
    private EndpointStatus status;
}
