package com.portals.trustwfh.model;

public enum Decision {
    ALLOW,
    FLAG;

    public static Decision fromConfidence(double confidence) {
        return confidence >= 0.5 ? ALLOW : FLAG;
    }
}

