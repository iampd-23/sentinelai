package com.sentinelai.evidence.repository;

import com.sentinelai.evidence.entity.incidentErrorGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface incidentErrorGroupRepository
        extends JpaRepository<incidentErrorGroup, UUID> {

    List<incidentErrorGroup> findByIncidentId(UUID incidentId);

    boolean existsByIncidentIdAndMessage(
            UUID incidentId,
            String message);
}