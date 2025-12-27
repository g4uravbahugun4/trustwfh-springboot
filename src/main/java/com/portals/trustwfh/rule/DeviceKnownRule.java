package com.portals.trustwfh.rule;

import com.portals.trustwfh.model.LoginContext;
import org.springframework.stereotype.Component;

@Component
public class DeviceKnownRule implements RuleEval {

    @Override
    public boolean evaluate(LoginContext context) {
        return context.isDeviceKnown();
    }
}

