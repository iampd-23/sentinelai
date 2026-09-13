package com.sentinelai.telemetry.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Instant;

public record logTelemetryRequest(

        @NotBlank(message = "Service name is required")
        @Size(max = 100, message = "Service name must not exceed 100 characters")
        String serviceName,

        @NotBlank(message = "Environment is required")
        @Size(max = 50, message = "Environment must not exceed 50 characters")
        String environment,

        @NotBlank(message = "Log level is required")
        @Size(max = 20, message = "Log level must not exceed 20 characters")
        String level,

        @NotBlank(message = "Log message is required")
        @Size(max = 5000, message = "Log message must not exceed 5000 characters")
        String message,

        @NotNull(message = "Timestamp is required")
        Instant timestamp
) {
}