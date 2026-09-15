package com.sentinelai.evidence.dto;

import java.util.UUID;

public record incidentErrorGroupResponse(
        UUID id,
        UUID incidentId,
        String message,
        long occurrenceCount
) {
}