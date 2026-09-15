package com.sentinelai.evidence.service;

import com.sentinelai.detection.dto.errorGroup;
import com.sentinelai.evidence.dto.incidentErrorGroupResponse;
import com.sentinelai.evidence.entity.incidentErrorGroup;
import com.sentinelai.evidence.repository.incidentErrorGroupRepository;
import com.sentinelai.incident.entity.incident;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class incidentErrorGroupService {

    private final incidentErrorGroupRepository incidentErrorGroupRepository;

    public incidentErrorGroupService(
            incidentErrorGroupRepository incidentErrorGroupRepository) {

        this.incidentErrorGroupRepository = incidentErrorGroupRepository;
    }

    public void saveErrorGroups(
            incident incident,
            List<errorGroup> errorGroups) {

        for (errorGroup group : errorGroups) {

            boolean alreadyExists =
                    incidentErrorGroupRepository
                            .existsByIncidentIdAndMessage(
                                    incident.getId(),
                                    group.message());

            if (alreadyExists) {
                continue;
            }

            incidentErrorGroup errorGroup =
                    new incidentErrorGroup(
                            incident,
                            group.message(),
                            group.count());

            incidentErrorGroupRepository.save(errorGroup);
        }
    }

    @Transactional(readOnly = true)
    public List<incidentErrorGroupResponse> getErrorGroupsForIncident(
            UUID incidentId) {

        return incidentErrorGroupRepository
                .findByIncidentId(incidentId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private incidentErrorGroupResponse toResponse(
            incidentErrorGroup group) {

        return new incidentErrorGroupResponse(
                group.getId(),
                group.getIncident().getId(),
                group.getMessage(),
                group.getOccurrenceCount());
    }
}