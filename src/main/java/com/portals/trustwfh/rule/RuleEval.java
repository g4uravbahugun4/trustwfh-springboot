package com.portals.trustwfh.rule;

import com.portals.trustwfh.model.LoginContext;

public interface RuleEval {
    boolean evaluate(LoginContext context);
}

