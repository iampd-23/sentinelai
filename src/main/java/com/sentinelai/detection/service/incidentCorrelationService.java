package com.sentinelai.detection.service;

import com.sentinelai.telemetry.entity.logTelemetry;
import com.sentinelai.telemetry.repository.logTelemetryRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class incidentCorrelationService {

    private static final long CORRELATION_WINDOW_MINUTES = 5;

    private final logTelemetryRepository logTelemetryRepository;

    public incidentCorrelationService(
            logTelemetryRepository logTelemetryRepository) {

        this.logTelemetryRepository = logTelemetryRepository;
    }

    public List<logTelemetry> findRelatedErrorLogs(
            String serviceName,
            String environment,
            Instant incidentDetectedAt) {

        Instant from =
                incidentDetectedAt.minus(
                        CORRELATION_WINDOW_MINUTES,
                        ChronoUnit.MINUTES);

        Instant to = incidentDetectedAt;

        return logTelemetryRepository.findErrorLogsForCorrelation(
                serviceName,
                environment,
                from,
                to);
    }
}