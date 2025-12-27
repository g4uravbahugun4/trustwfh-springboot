package com.portals.trustwfh.model;

public class ConfidenceResult {

    private final double confidence;
    private final Decision decision;

    public ConfidenceResult(double confidence, Decision decision) {
        this.confidence = confidence;
        this.decision = decision;
    }

    public double getConfidence() {
        return confidence;
    }

    public Decision getDecision() {
        return decision;
    }
}

