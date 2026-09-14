package com.sentinelai.detection.service;

import com.sentinelai.telemetry.repository.logTelemetryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class errorSpikeDetectionService {

    private static final Logger log =
            LoggerFactory.getLogger(errorSpikeDetectionService.class);

    private static final long ERROR_THRESHOLD = 10;

    private final logTelemetryRepository logTelemetryRepository;

    public errorSpikeDetectionService(
            logTelemetryRepository logTelemetryRepository) {

        this.logTelemetryRepository = logTelemetryRepository;
    }

    public boolean hasErrorSpike(
            String serviceName,
            String environment) {

        Instant to = Instant.now();
        Instant from = to.minus(1, ChronoUnit.MINUTES);

        long errorCount =
                logTelemetryRepository.countErrors(
                        serviceName,
                        environment,
                        from,
                        to);

        log.info(
                "Error spike check: service={}, environment={}, errorCount={}, threshold={}",
                serviceName,
                environment,
                errorCount,
                ERROR_THRESHOLD);

        return errorCount >= ERROR_THRESHOLD;
    }
}