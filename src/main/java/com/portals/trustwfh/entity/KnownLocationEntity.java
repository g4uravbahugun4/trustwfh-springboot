package com.portals.trustwfh.entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(
        name = "known_locations",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"userId", "locationKey"})
        }
)
public class KnownLocationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;


    private String locationKey;

    private Instant firstSeen;
    private Instant lastSeen;

    protected KnownLocationEntity() {}

    public KnownLocationEntity(String userId, String locationKey) {
        this.userId = userId;
        this.locationKey = locationKey;
        this.firstSeen = Instant.now();
        this.lastSeen = Instant.now();
    }

    public void updateLastSeen() {
        this.lastSeen = Instant.now();
    }
}

