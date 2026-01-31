package com.portals.trustwfh.service;

import com.portals.trustwfh.dto.ConfidenceResult;
import com.portals.trustwfh.dto.PunchInEventMessage;

import com.portals.trustwfh.model.LoginContext;

import com.portals.trustwfh.rule.RuleEval;
import com.portals.trustwfh.rule.RuleResult;
import com.portals.trustwfh.utils.ConfidenceCalculator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PunchInService {

    private final List<RuleEval> rules;
    private final ConfidenceCalculator calculator;
    private final KafkaProducerService kafkaProducerService;

    public PunchInService(List<RuleEval> rules,
            ConfidenceCalculator calculator,
            KafkaProducerService kafkaProducerService) {
        this.rules = rules;
        this.calculator = calculator;
        this.kafkaProducerService = kafkaProducerService;
    }

    public ConfidenceResult punchIn(String userId, LoginContext context) {
        // Enforce that context matches the authenticated user
        if (!context.getUserId().equals(userId)) {
            throw new SecurityException("User ID mismatch!");
        }

        // Evaluate Risk
        List<RuleResult> results = rules.stream()
                .map(rule -> rule.evaluate(context))
                .toList();

        double confidence = calculator.calculate(results);

        // Create DTO for Kafka
        List<PunchInEventMessage.SignalMessage> signalMessages = results.stream()
                .map(result -> {
                    double weight = calculator.weightOf(result.getRuleName());
                    double ruleScore = result.isPassed() ? weight : 0.0;
                    return new PunchInEventMessage.SignalMessage(
                            result.getRuleName(),
                            ruleScore,
                            weight,
                            result.getRemark());
                }).toList();

        PunchInEventMessage eventMessage = new PunchInEventMessage(
                userId,
                confidence,
                context.getIpAddress(),
                context.getDeviceFingerprint(),
                context.getDeviceToken(),
                signalMessages);

        // Publish to Kafka (Async)
        kafkaProducerService.sendPunchInEvent(eventMessage);

        // We don't issue a NEW token here, we just return the result of the punch-in
        // attempt
        // The token is already valid (passed via Auth header)
        return new ConfidenceResult(confidence);
    }
}
