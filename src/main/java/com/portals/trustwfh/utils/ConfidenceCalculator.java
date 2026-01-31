package com.portals.trustwfh.utils;

import com.portals.trustwfh.rule.RuleResult;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConfidenceCalculator {

    public double calculate(List<RuleResult> results) {

        double score = 0.0;

        for (RuleResult result : results) {
            if (result.isPassed()) {
                score += weightOf(result.getRuleName());
            }
        }

        return score;
    }

    public double weightOf(String ruleName) {
        return switch (ruleName) {
            case "DEVICE_KNOWN" -> 0.6;
            case "LOCATION_KNOWN" -> 0.2;
            default -> 0.0;
        };
    }
}
