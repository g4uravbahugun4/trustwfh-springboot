package com.portals.trustwfh.rule;

import com.portals.trustwfh.entity.KnownDeviceEntity;
import com.portals.trustwfh.model.LoginContext;
import com.portals.trustwfh.repository.KnownDeviceRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class DeviceKnownRule implements RuleEval {
    private final KnownDeviceRepository knownDeviceRepository;

    public DeviceKnownRule(KnownDeviceRepository knownDeviceRepository) {
        this.knownDeviceRepository = knownDeviceRepository;
    }

    @Override
    public RuleResult evaluate(LoginContext context) {

        // 1. Enterprise / Company Managed Device (Highest Trust)
        if (context.hasClientCert()) {
            Optional<KnownDeviceEntity> corporateDevice = knownDeviceRepository.findByUserIdAndCertThumbprint(
                    context.getUserId(),
                    context.getCompanyDeviceCertId());

            if (corporateDevice.isPresent()) {
                corporateDevice.get().updateLastSeen(context.getIpAddress());
                knownDeviceRepository.save(corporateDevice.get());
                return new RuleResult("DEVICE_KNOWN", true, "Company Managed Device Verified");
            }
        }

        // 2. Personal Trusted Device (Cookie based)
        if (context.hasDeviceToken()) {
            Optional<KnownDeviceEntity> personalDevice = knownDeviceRepository.findByUserIdAndToken(
                    context.getUserId(),
                    context.getDeviceToken());

            if (personalDevice.isPresent()) {
                personalDevice.get().updateLastSeen(context.getIpAddress());
                knownDeviceRepository.save(personalDevice.get());
                return new RuleResult("DEVICE_KNOWN", true, "Trusted Personal Device Verified");
            }
        }

        // 3. Fallback Heuristic (Fingerprint)
        // This handles cases where cookies might be cleared but the device is
        // consistent
        List<KnownDeviceEntity> fingerprintMatches = knownDeviceRepository.findByUserIdAndFingerprint(
                context.getUserId(),
                context.getDeviceFingerprint());

        if (!fingerprintMatches.isEmpty()) {
            // We found a device with the same characteristics (UA, Language, etc.)
            // We can consider this "Soft Known".
            // For now, we return true but ideally this would trigger a step-up challenge.
            KnownDeviceEntity matched = fingerprintMatches.get(0);
            matched.updateLastSeen(context.getIpAddress());
            knownDeviceRepository.save(matched);
            return new RuleResult("DEVICE_KNOWN", true, "Device Characteristics Recognized (Fingerprint Match)");
        }

        return new RuleResult("DEVICE_KNOWN", false, "Unknown Device");
    }

}
