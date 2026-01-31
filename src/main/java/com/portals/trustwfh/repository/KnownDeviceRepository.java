package com.portals.trustwfh.repository;

import com.portals.trustwfh.entity.KnownDeviceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface KnownDeviceRepository
                extends JpaRepository<KnownDeviceEntity, Long> {

        Optional<KnownDeviceEntity> findByUserIdAndDeviceSignature(String userId, String deviceSignature);

        boolean existsByUserIdAndDeviceSignature(
                        String userId,
                        String deviceSignature);

        Optional<KnownDeviceEntity> findByUserIdAndToken(String userId, String token);

        Optional<KnownDeviceEntity> findByUserIdAndCertThumbprint(String userId, String certThumbprint);

        // Fingerprint matches might correspond to multiple old records if users cleared
        // cookies often
        List<KnownDeviceEntity> findByUserIdAndFingerprint(String userId, String fingerprint);

}
