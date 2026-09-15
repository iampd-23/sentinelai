package com.sentinelai.evidence.service;

import com.sentinelai.evidence.dto.incidentEvidenceResponse;
import com.sentinelai.evidence.entity.incidentEvidence;
import com.sentinelai.evidence.repository.incidentEvidenceRepository;
import com.sentinelai.incident.entity.incident;
import com.sentinelai.telemetry.entity.logTelemetry;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class incidentEvidenceService {

    private final incidentEvidenceRepository incidentEvidenceRepository;

    public incidentEvidenceService(
            incidentEvidenceRepository incidentEvidenceRepository) {

        this.incidentEvidenceRepository = incidentEvidenceRepository;
    }

    public void saveEvidence(
            incident incident,
            List<logTelemetry> relatedLogs) {

        for (logTelemetry telemetry : relatedLogs) {

            boolean alreadyExists =
                    incidentEvidenceRepository
                            .existsByIncidentIdAndTelemetryEventId(
                                    incident.getId(),
                                    telemetry.getEventId());

            if (alreadyExists) {
                continue;
            }

            incidentEvidence evidence =
                    new incidentEvidence(
                            incident,
                            telemetry,
                            Instant.now());

            incidentEvidenceRepository.save(evidence);
        }
    }

    @Transactional(readOnly = true)
    public List<incidentEvidenceResponse> getEvidenceForIncident(
            UUID incidentId) {

        return incidentEvidenceRepository
                .findByIncidentId(incidentId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private incidentEvidenceResponse toResponse(
            incidentEvidence evidence) {

        logTelemetry telemetry = evidence.getTelemetry();

        return new incidentEvidenceResponse(
                evidence.getId(),
                evidence.getIncident().getId(),
                telemetry.getEventId(),
                telemetry.getServiceName(),
                telemetry.getEnvironment(),
                telemetry.getLevel(),
                telemetry.getMessage(),
                telemetry.getTimestamp(),
                telemetry.getReceivedAt(),
                evidence.getCorrelatedAt()
        );
    }
}