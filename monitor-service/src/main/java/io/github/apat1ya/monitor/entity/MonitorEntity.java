package io.github.apat1ya.monitor.entity;

import io.github.apat1ya.monitor.entity.member.MonitorMember;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class MonitorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long userId;
    @OneToMany(mappedBy = "monitor",
    cascade = CascadeType.ALL,
    orphanRemoval = true)
    private List<MonitorMember> members;
    @Column(nullable = false)
    private String target;
    private String description;
    @Column(nullable = false)
    private boolean active;
}
