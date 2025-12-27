package com.portals.trustwfh.utils;

import org.springframework.stereotype.Component;

@Component
public class ConfidenceCalculator {

    public double calculate(boolean deviceKnown) {
        return deviceKnown ? 0.8 : 0.3;
    }
}

