package com.portals.trustwfh.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "punchin_event_signals")
public class PunchinEventSignalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String signalName;

    private double score;

    private double weight;

    private String remark;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "punchin_event_id")
    private PunchinEventEntity punchinEvent;

    protected PunchinEventSignalEntity() {
    }

    public PunchinEventSignalEntity(
            String signalName,
            double score,
            double weight,
            String remark) {
        this.signalName = signalName;
        this.score = score;
        this.weight = weight;
        this.remark = remark;
    }

    void setPunchinEvent(PunchinEventEntity event) {
        this.punchinEvent = event;
    }
}
