package io.github.apat1ya.monitor.entity;

import io.github.apat1ya.monitor.entity.member.MonitorMember;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
    @OneToMany(mappedBy = "monitor")
    private List<MonitorMember> members;
    @Column(nullable = false)
    private String target;
    private String description;
    @Column(nullable = false)
    private boolean active;
}
