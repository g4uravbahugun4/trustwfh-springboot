package com.portals.trustwfh.service;

import com.portals.trustwfh.entity.KnownLocationEntity;
import com.portals.trustwfh.repository.KnownLocationRepository;
import com.portals.trustwfh.repository.KnownDeviceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TrustLearningService {

    private final KnownLocationRepository locationRepository;
    private final KnownDeviceRepository deviceRepository;

    public TrustLearningService(KnownLocationRepository locationRepository,
            KnownDeviceRepository deviceRepository) {
        this.locationRepository = locationRepository;
        this.deviceRepository = deviceRepository;
    }

    /**
     * If a punch-in was successful with high confidence, we "learn"
     * that this context (IP/Device) is now trusted.
     */
    @Transactional
    public void learnFromEvent(String userId, String ipAddress, String deviceToken, String deviceFingerprint,
            double confidence) {
        // Only learn if confidence is very high (e.g. 1.0)
        if (confidence < 0.9) {
            return;
        }

        // 1. Learn/Update Location
        if (ipAddress != null) {
            locationRepository.findByUserIdAndLocationKey(userId, ipAddress)
                    .ifPresentOrElse(
                            KnownLocationEntity::updateLastSeen,
                            () -> locationRepository.save(new KnownLocationEntity(userId, ipAddress)));
        }

        // 2. Update Device Last Seen
        if (deviceToken != null) {
            deviceRepository.findByUserIdAndToken(userId, deviceToken)
                    .ifPresent(device -> {
                        device.updateLastSeen(ipAddress);
                        deviceRepository.save(device);
                    });
        }
    }
}
