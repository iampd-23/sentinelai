package com.sentinelai.incident.dto;

import com.sentinelai.incident.entity.incidentSeverity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record incidentCreateRequest(

        @NotNull(message = "Service ID is required")
        UUID serviceId,

        @NotBlank(message = "Incident title is required")
        @Size(
                max = 255,
                message = "Incident title must not exceed 255 characters"
        )
        String title,

        @NotNull(message = "Incident severity is required")
        incidentSeverity severity

) {
}