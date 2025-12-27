package com.portals.trustwfh.model;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "login_events")
public class LoginEventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private boolean deviceKnown;

    @Column(nullable = false)
    private double confidence;

    @Column(nullable = false)
    private String decision;

    @Column(nullable = false)
    private Instant createdAt;

    protected LoginEventEntity() { }

    public LoginEventEntity(boolean deviceKnown,
                            double confidence,
                            String decision) {
        this.deviceKnown = deviceKnown;
        this.confidence = confidence;
        this.decision = decision;
        this.createdAt = Instant.now();
    }
}

