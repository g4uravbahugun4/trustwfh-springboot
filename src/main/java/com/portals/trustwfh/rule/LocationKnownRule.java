package com.portals.trustwfh.rule;

import com.portals.trustwfh.model.LoginContext;
import com.portals.trustwfh.repository.KnownLocationRepository;
import org.springframework.stereotype.Component;

@Component
public class LocationKnownRule implements RuleEval {
    private final KnownLocationRepository locationRepository;

    public LocationKnownRule(KnownLocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public RuleResult evaluate(LoginContext context) {
        String userId = context.getUserId();
        String locationKey = deriveLocationKey(context);

        boolean known = locationRepository.existsByUserIdAndLocationKey(
                userId,
                locationKey);

        return new RuleResult("LOCATION_KNOWN", known, "Location Known");
    }

    private String deriveLocationKey(LoginContext context) {
        return context.getIpAddress(); // placeholder
    }
}
