package com.sentinelai.telemetry.service;

import com.sentinelai.telemetry.entity.logTelemetry;
import com.sentinelai.telemetry.event.logTelemetryEvent;
import com.sentinelai.telemetry.repository.logTelemetryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class logTelemetryEventProcessor {

    private final logTelemetryRepository logTelemetryRepository;

    public logTelemetryEventProcessor(
            logTelemetryRepository logTelemetryRepository
    ) {
        this.logTelemetryRepository = logTelemetryRepository;
    }

    @Transactional
    public void process(logTelemetryEvent event) {

        logTelemetry telemetry = new logTelemetry(
                event.eventId(),
                event.serviceName(),
                event.environment(),
                event.level(),
                event.message(),
                event.timestamp(),
                event.receivedAt()
        );

        logTelemetryRepository.save(telemetry);
    }
}