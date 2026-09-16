package com.sentinelai.rca.repository;

import com.sentinelai.rca.entity.rootCauseCandidate;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface rootCauseCandidateRepository
        extends JpaRepository<rootCauseCandidate, UUID> {

    @EntityGraph(attributePaths = {"incident", "candidateService"})
    List<rootCauseCandidate> findByIncidentId(UUID incidentId);

    boolean existsByIncidentIdAndCandidateServiceId(
            UUID incidentId,
            UUID candidateServiceId);
}