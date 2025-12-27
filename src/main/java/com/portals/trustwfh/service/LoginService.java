package com.portals.trustwfh.service;

import com.portals.trustwfh.model.*;
import com.portals.trustwfh.repository.LoginEventRepository;
import com.portals.trustwfh.rule.RuleEval;
import com.portals.trustwfh.utils.ConfidenceCalculator;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final RuleEval ruleEvaluator;
    private final ConfidenceCalculator calculator;
    private final LoginEventRepository repository;

    public LoginService(RuleEval ruleEvaluator,
                        ConfidenceCalculator calculator,
                        LoginEventRepository repository) {
        this.ruleEvaluator = ruleEvaluator;
        this.calculator = calculator;
        this.repository = repository;
    }

    public ConfidenceResult evaluate(LoginRequest request) {

        LoginContext context = LoginContext.from(request);

        boolean deviceKnown = ruleEvaluator.evaluate(context);
        double confidence = calculator.calculate(deviceKnown);
        Decision decision = Decision.fromConfidence(confidence);

        repository.save(
                new LoginEventEntity(
                        deviceKnown,
                        confidence,
                        decision.name()
                )
        );

        return new ConfidenceResult(confidence, decision);
    }
}


