package com.sentinelai.detection.service;

import com.sentinelai.incident.entity.incidentStatus;
import com.sentinelai.incident.entity.incidentSeverity;
import com.sentinelai.incident.repository.incidentRepository;
import com.sentinelai.incident.service.incidentService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class incidentDetectionService {

    private final errorSpikeDetectionService errorSpikeDetectionService;
    private final incidentService incidentService;
    private final incidentRepository incidentRepository;

    public incidentDetectionService(
            errorSpikeDetectionService errorSpikeDetectionService,
            incidentService incidentService,
            incidentRepository incidentRepository) {

        this.errorSpikeDetectionService = errorSpikeDetectionService;
        this.incidentService = incidentService;
        this.incidentRepository = incidentRepository;
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
            return;
        }

        incidentService.createIncident(
                serviceId,
                "Error spike detected in " + serviceName,
                incidentSeverity.HIGH);
    }
}