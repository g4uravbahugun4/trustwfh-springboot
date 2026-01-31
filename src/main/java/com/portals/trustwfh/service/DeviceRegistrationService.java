package com.portals.trustwfh.service;

import com.portals.trustwfh.entity.KnownDeviceEntity;
import com.portals.trustwfh.repository.KnownDeviceRepository;
import org.springframework.stereotype.Service;

@Service
public class DeviceRegistrationService {

        private final KnownDeviceRepository repository;

        public DeviceRegistrationService(KnownDeviceRepository repository) {
                this.repository = repository;
        }

        public void registerDevice(
                        String userId,
                        String deviceSignature,
                        String ip,
                        String token,
                        String fingerprint,
                        String certThumbprint) {
                repository.findByUserIdAndDeviceSignature(userId, deviceSignature)
                                .ifPresentOrElse(
                                                entity -> entity.updateLastSeen(ip),
                                                () -> repository.save(
                                                                new KnownDeviceEntity(userId, deviceSignature, token,
                                                                                fingerprint, certThumbprint, ip)));
        }
}
