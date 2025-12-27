package com.portals.trustwfh.repository;

import com.portals.trustwfh.model.LoginEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginEventRepository
        extends JpaRepository<LoginEventEntity, Long> {
}

