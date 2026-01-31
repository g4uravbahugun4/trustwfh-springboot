package com.portals.trustwfh.dto;

public class ConfidenceResult {

    private final double confidence;

    public ConfidenceResult(double confidence) {
        this.confidence = confidence;
    }

    public double getConfidence() {
        return confidence;
    }
}
