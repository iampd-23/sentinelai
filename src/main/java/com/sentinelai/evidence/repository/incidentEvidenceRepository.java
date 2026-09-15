package com.sentinelai.evidence.repository;

import com.sentinelai.evidence.entity.incidentEvidence;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface incidentEvidenceRepository
        extends JpaRepository<incidentEvidence, UUID> {

    List<incidentEvidence> findByIncidentId(UUID incidentId);

    boolean existsByIncidentIdAndTelemetryEventId(
            UUID incidentId,
            UUID telemetryEventId);
}