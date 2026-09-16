package com.sentinelai.dependency.dto;

import com.sentinelai.dependency.entity.dependencyType;

import java.time.Instant;
import java.util.UUID;

public record serviceDependencyResponse(
        UUID id,
        UUID sourceServiceId,
        String sourceServiceName,
        UUID targetServiceId,
        String targetServiceName,
        dependencyType dependencyType,
        Instant createdAt) {
}