package com.sentinelai.incident.dto;

import com.sentinelai.incident.entity.incident;
import com.sentinelai.incident.entity.incidentSeverity;
import com.sentinelai.incident.entity.incidentStatus;

import java.time.Instant;
import java.util.UUID;

public record incidentResponse(

        UUID id,

        UUID serviceId,

        String serviceName,

        String environment,

        String title,

        incidentSeverity severity,

        incidentStatus status,

        Instant detectedAt,

        Instant acknowledgedAt,

        Instant resolvedAt,

        Instant updatedAt

) {

    public static incidentResponse fromEntity(
            incident incident
    ) {
        return new incidentResponse(
                incident.getId(),
                incident.getMonitoredService().getId(),
                incident.getMonitoredService().getName(),
                incident.getMonitoredService().getEnvironment(),
                incident.getTitle(),
                incident.getSeverity(),
                incident.getStatus(),
                incident.getDetectedAt(),
                incident.getAcknowledgedAt(),
                incident.getResolvedAt(),
                incident.getUpdatedAt()
        );
    }
}