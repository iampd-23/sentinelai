package com.sentinelai.service.dto;

import com.sentinelai.service.entity.serviceStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record monitoredServiceCreateRequest(

        @NotBlank(message = "Service name is required")
        @Size(max = 100, message = "Service name must not exceed 100 characters")
        String name,

        @NotBlank(message = "Environment is required")
        @Size(max = 50, message = "Environment must not exceed 50 characters")
        String environment,

        @NotBlank(message = "Owner team is required")
        @Size(max = 100, message = "Owner team must not exceed 100 characters")
        String ownerTeam,

        @NotNull(message = "Service status is required")
        serviceStatus status
) {
}