package com.portals.trustwfh.rule;

import com.portals.trustwfh.model.LoginContext;

public interface RuleEval {
    RuleResult evaluate(LoginContext context);
}

