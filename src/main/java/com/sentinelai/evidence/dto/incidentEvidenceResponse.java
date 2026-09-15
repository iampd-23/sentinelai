package com.sentinelai.evidence.dto;

import java.time.Instant;
import java.util.UUID;

public record incidentEvidenceResponse(
        UUID id,
        UUID incidentId,
        UUID telemetryId,
        String serviceName,
        String environment,
        String level,
        String message,
        Instant eventTimestamp,
        Instant receivedAt,
        Instant correlatedAt
) {
}