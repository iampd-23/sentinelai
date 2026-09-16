package com.sentinelai.rca.service;

import com.sentinelai.dependency.entity.serviceDependency;
import com.sentinelai.dependency.repository.serviceDependencyRepository;
import com.sentinelai.evidence.entity.incidentErrorGroup;
import com.sentinelai.evidence.repository.incidentErrorGroupRepository;
import com.sentinelai.incident.entity.incident;
import com.sentinelai.incident.repository.incidentRepository;
import com.sentinelai.rca.entity.rootCauseCandidate;
import com.sentinelai.rca.repository.rootCauseCandidateRepository;
import com.sentinelai.service.entity.monitoredService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Service
@Transactional
public class rootCauseAnalysisService {

    private final incidentRepository incidentRepository;
    private final incidentErrorGroupRepository incidentErrorGroupRepository;
    private final serviceDependencyRepository serviceDependencyRepository;
    private final rootCauseCandidateRepository rootCauseCandidateRepository;

    public rootCauseAnalysisService(
            incidentRepository incidentRepository,
            incidentErrorGroupRepository incidentErrorGroupRepository,
            serviceDependencyRepository serviceDependencyRepository,
            rootCauseCandidateRepository rootCauseCandidateRepository) {

        this.incidentRepository = incidentRepository;
        this.incidentErrorGroupRepository = incidentErrorGroupRepository;
        this.serviceDependencyRepository = serviceDependencyRepository;
        this.rootCauseCandidateRepository = rootCauseCandidateRepository;
    }

    public List<rootCauseCandidate> analyzeIncident(UUID incidentId) {

        incident incident = incidentRepository.findById(incidentId)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Incident not found: " + incidentId));

        UUID sourceServiceId = incident.getMonitoredService().getId();

        List<incidentErrorGroup> errorGroups =
                incidentErrorGroupRepository.findByIncidentId(incidentId);

        List<serviceDependency> dependencies =
                serviceDependencyRepository.findBySourceServiceId(sourceServiceId);

        for (serviceDependency dependency : dependencies) {

            monitoredService targetService = dependency.getTargetService();

            boolean alreadyExists =
                    rootCauseCandidateRepository
                            .existsByIncidentIdAndCandidateServiceId(
                                    incidentId,
                                    targetService.getId());

            if (alreadyExists) {
                continue;
            }

            for (incidentErrorGroup errorGroup : errorGroups) {

                String message =
                        errorGroup.getMessage().toLowerCase(Locale.ROOT);

                String targetServiceName =
                        targetService.getName().toLowerCase(Locale.ROOT);

                if (message.contains(targetServiceName)) {

                    double confidence =
                            calculateConfidence(
                                    errorGroup.getOccurrenceCount());

                    String reason = String.format(
                            "Incident service '%s' depends on '%s' via %s, "
                                    + "and the error group '%s' indicates a "
                                    + "failure associated with that dependency.",
                            incident.getMonitoredService().getName(),
                            targetService.getName(),
                            dependency.getDependencyType(),
                            errorGroup.getMessage());

                    rootCauseCandidate candidate =
                            new rootCauseCandidate(
                                    incident,
                                    targetService,
                                    reason,
                                    confidence);

                    rootCauseCandidateRepository.save(candidate);

                    break;
                }
            }
        }

        return rootCauseCandidateRepository.findByIncidentId(incidentId);
    }

    @Transactional(readOnly = true)
    public List<rootCauseCandidate> getCandidates(UUID incidentId) {

        return rootCauseCandidateRepository.findByIncidentId(incidentId);
    }

    private double calculateConfidence(long occurrenceCount) {

        if (occurrenceCount >= 10) {
            return 0.95;
        }

        if (occurrenceCount >= 5) {
            return 0.85;
        }

        if (occurrenceCount >= 3) {
            return 0.75;
        }

        return 0.65;
    }
}