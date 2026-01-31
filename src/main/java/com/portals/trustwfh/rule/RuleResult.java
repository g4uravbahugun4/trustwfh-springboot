package com.portals.trustwfh.rule;

public class RuleResult {

    private final String ruleName;
    private final boolean passed;
    private final String remark;

    public RuleResult(String ruleName, boolean passed, String remark) {
        this.ruleName = ruleName;
        this.passed = passed;
        this.remark = remark;
    }

    public String getRuleName() {
        return ruleName;
    }

    public boolean isPassed() {
        return passed;
    }

    public String getRemark() {
        return remark;
    }
}
