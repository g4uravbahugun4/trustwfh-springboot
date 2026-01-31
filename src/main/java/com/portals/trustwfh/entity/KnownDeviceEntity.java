package com.portals.trustwfh.entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(
        name = "known_devices",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"userId", "deviceSignature"}),
                @UniqueConstraint(columnNames = {"token"})
        },
        indexes = {
                @Index(columnList = "userId, fingerprint"),
                @Index(columnList = "certThumbprint")
        }
)
public class KnownDeviceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;

    private String deviceSignature;
    
    // Persistent Trust Token
    private String token;
    
    // Heuristic Fingerprint
    private String fingerprint; 
    
    // Company Certificate Thumbprint/ID
    private String certThumbprint;

    private Instant firstSeen;

    private Instant lastSeen;
    
    private String lastIp;

    protected KnownDeviceEntity() {}

    public KnownDeviceEntity(String userId, String deviceSignature, String token, String fingerprint, String certThumbprint, String lastIp) {
        this.userId = userId;
        this.deviceSignature = deviceSignature;
        this.token = token;
        this.fingerprint = fingerprint;
        this.certThumbprint = certThumbprint;
        this.lastIp = lastIp;
        this.firstSeen = Instant.now();
        this.lastSeen = Instant.now();
    }

    public void updateLastSeen(String ip) {
        this.lastSeen = Instant.now();
        this.lastIp = ip;
    }
    
    public String getToken() {
        return token;
    }
    
    public String getFingerprint() {
        return fingerprint;
    }
    
    public String getCertThumbprint() {
        return certThumbprint;
    }

    // getters only
}

