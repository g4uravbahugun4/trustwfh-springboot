package com.portals.trustwfh.entity;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "punchin_events")
public class PunchinEventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double confidence;

    private String userId;

    private Instant createdAt;

    @OneToMany(mappedBy = "punchinEvent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PunchinEventSignalEntity> signals = new ArrayList<>();

    protected PunchinEventEntity() {
    }

    public PunchinEventEntity(String userId, double confidence) {
        this.userId = userId;
        this.confidence = confidence;
        this.createdAt = Instant.now();
    }

    public void addSignal(PunchinEventSignalEntity signal) {
        signals.add(signal);
        signal.setPunchinEvent(this);
    }

    public String getUserId() {
        return userId;
    }

}
