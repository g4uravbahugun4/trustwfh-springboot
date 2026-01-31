package com.portals.trustwfh.repository;

import com.portals.trustwfh.entity.PunchinEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PunchinEventRepository
        extends JpaRepository<PunchinEventEntity, Long> {
}
