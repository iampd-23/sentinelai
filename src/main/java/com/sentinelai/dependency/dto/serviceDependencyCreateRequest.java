package com.sentinelai.dependency.dto;

import com.sentinelai.dependency.entity.dependencyType;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record serviceDependencyCreateRequest(
        @NotNull UUID sourceServiceId,
        @NotNull UUID targetServiceId,
        @NotNull dependencyType dependencyType) {
}