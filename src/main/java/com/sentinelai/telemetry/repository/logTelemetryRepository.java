package com.sentinelai.telemetry.repository;

import com.sentinelai.telemetry.entity.logTelemetry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface logTelemetryRepository
        extends JpaRepository<logTelemetry, UUID> {
}