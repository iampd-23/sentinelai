package com.sentinelai.detection.service;

import com.sentinelai.incident.entity.incident;
import com.sentinelai.incident.entity.incidentSeverity;
import com.sentinelai.incident.entity.incidentStatus;
import com.sentinelai.incident.repository.incidentRepository;
import com.sentinelai.incident.service.incidentService;
import com.sentinelai.telemetry.entity.logTelemetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class incidentDetectionService {

    private static final Logger log =
            LoggerFactory.getLogger(incidentDetectionService.class);

    private final errorSpikeDetectionService errorSpikeDetectionService;
    private final incidentService incidentService;
    private final incidentRepository incidentRepository;
    private final incidentCorrelationService incidentCorrelationService;

    public incidentDetectionService(
            errorSpikeDetectionService errorSpikeDetectionService,
            incidentService incidentService,
            incidentRepository incidentRepository,
            incidentCorrelationService incidentCorrelationService) {

        this.errorSpikeDetectionService = errorSpikeDetectionService;
        this.incidentService = incidentService;
        this.incidentRepository = incidentRepository;
        this.incidentCorrelationService = incidentCorrelationService;
    }

    public void detectForService(
            UUID serviceId,
            String serviceName,
            String environment) {

        boolean errorSpike =
                errorSpikeDetectionService.hasErrorSpike(
                        serviceName,
                        environment);

        if (!errorSpike) {
            return;
        }

        boolean incidentAlreadyOpen =
                incidentRepository.existsByMonitoredServiceIdAndStatus(
                        serviceId,
                        incidentStatus.OPEN);

        if (incidentAlreadyOpen) {
            log.info(
                    "Skipping incident creation: OPEN incident already exists for service={} / environment={}",
                    serviceName,
                    environment);
            return;
        }

        incident incident =
                incidentService.createIncident(
                        serviceId,
                        "Error spike detected in " + serviceName,
                        incidentSeverity.HIGH);

        Instant incidentDetectedAt = incident.getDetectedAt();

        List<logTelemetry> relatedLogs =
                incidentCorrelationService.findRelatedErrorLogs(
                        serviceName,
                        environment,
                        incidentDetectedAt);

        log.info(
                "Incident created: id={}, service={}, relatedErrorLogs={}",
                incident.getId(),
                serviceName,
                relatedLogs.size());

        relatedLogs.stream()
                .limit(5)
                .forEach(logTelemetry ->
                        log.info(
                                "Related log: timestamp={}, level={}, message={}",
                                logTelemetry.getReceivedAt(),
                                logTelemetry.getLevel(),
                                logTelemetry.getMessage()));
    }
}