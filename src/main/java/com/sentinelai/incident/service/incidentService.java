package com.sentinelai.incident.service;

import com.sentinelai.incident.entity.incident;
import com.sentinelai.incident.entity.incidentSeverity;
import com.sentinelai.incident.entity.incidentStatus;
import com.sentinelai.incident.repository.incidentRepository;
import com.sentinelai.service.entity.monitoredService;
import com.sentinelai.service.repository.monitoredServiceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sentinelai.common.exception.incidentNotFoundException;
import com.sentinelai.common.exception.invalidIncidentStateException;
import com.sentinelai.common.exception.serviceNotFoundException;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class incidentService {

        private final incidentRepository incidentRepository;
        private final monitoredServiceRepository monitoredServiceRepository;

        public incidentService(
                        incidentRepository incidentRepository,
                        monitoredServiceRepository monitoredServiceRepository) {
                this.incidentRepository = incidentRepository;
                this.monitoredServiceRepository = monitoredServiceRepository;
        }

        public incident createIncident(
                        UUID serviceId,
                        String title,
                        incidentSeverity severity) {

                monitoredService monitoredService = monitoredServiceRepository.findById(serviceId)
                                .orElseThrow(() -> new serviceNotFoundException(
                                                "Service not found: " + serviceId));

                Instant detectedAt = Instant.now();

                incident incident = new incident(
                                monitoredService,
                                title,
                                severity,
                                incidentStatus.OPEN,
                                detectedAt);

                return incidentRepository.save(incident);
        }

        @Transactional(readOnly = true)
        public incident getIncidentById(UUID id) {
                return incidentRepository.findById(id)
                                .orElseThrow(() -> new incidentNotFoundException(
                                                "Incident not found: " + id));
        }

        @Transactional(readOnly = true)
        public List<incident> getAllIncidents() {
                return incidentRepository.findAll();
        }

        @Transactional(readOnly = true)
        public List<incident> getIncidentsByStatus(
                        incidentStatus status) {
                return incidentRepository.findByStatus(status);
        }

        @Transactional(readOnly = true)
        public List<incident> getIncidentsBySeverity(
                        incidentSeverity severity) {
                return incidentRepository.findBySeverity(severity);
        }

        @Transactional(readOnly = true)
        public List<incident> getIncidentsByService(
                        UUID serviceId) {
                return incidentRepository.findByMonitoredServiceId(serviceId);
        }

        public incident acknowledgeIncident(UUID id) {

                incident incident = getIncidentById(id);

                if (incident.getStatus() != incidentStatus.OPEN) {
                        throw new invalidIncidentStateException(
                                        "Only open incidents can be acknowledged");
                }

                incident.acknowledge(Instant.now());

                return incident;
        }

        public incident resolveIncident(UUID id) {

                incident incident = getIncidentById(id);

                if (incident.getStatus() == incidentStatus.RESOLVED) {
                        throw new invalidIncidentStateException(
                                        "Incident is already resolved");
                }

                incident.resolve(Instant.now());

                return incident;
        }
}