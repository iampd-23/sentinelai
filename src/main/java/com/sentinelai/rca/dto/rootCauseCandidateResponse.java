package com.sentinelai.rca.dto;

import java.util.UUID;

public record rootCauseCandidateResponse(
        UUID id,
        UUID incidentId,
        UUID candidateServiceId,
        String candidateServiceName,
        String reason,
        double confidence) {
}