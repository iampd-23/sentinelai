package com.sentinelai.service.dto;

import com.sentinelai.service.entity.monitoredService;
import com.sentinelai.service.entity.serviceStatus;

import java.time.Instant;
import java.util.UUID;

public record monitoredServiceResponse(

        UUID id,

        String name,

        String environment,

        String ownerTeam,

        serviceStatus status,

        Instant createdAt,

        Instant updatedAt

) {

    public static monitoredServiceResponse fromEntity(
            monitoredService service
    ) {
        return new monitoredServiceResponse(
                service.getId(),
                service.getName(),
                service.getEnvironment(),
                service.getOwnerTeam(),
                service.getStatus(),
                service.getCreatedAt(),
                service.getUpdatedAt()
        );
    }
}