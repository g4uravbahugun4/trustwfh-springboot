package com.portals.trustwfh.repository;

import com.portals.trustwfh.entity.KnownLocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KnownLocationRepository
                extends JpaRepository<KnownLocationEntity, Long> {

        boolean existsByUserIdAndLocationKey(
                        String userId,
                        String locationKey);

        java.util.Optional<com.portals.trustwfh.entity.KnownLocationEntity> findByUserIdAndLocationKey(
                        String userId,
                        String locationKey);
}
