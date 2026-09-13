package com.sentinelai.incident.controller;

import com.sentinelai.incident.dto.incidentCreateRequest;
import com.sentinelai.incident.dto.incidentResponse;
import com.sentinelai.incident.entity.incident;
import com.sentinelai.incident.entity.incidentSeverity;
import com.sentinelai.incident.entity.incidentStatus;
import com.sentinelai.incident.service.incidentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/incidents")
public class incidentController {

    private final incidentService incidentService;

    public incidentController(
            incidentService incidentService
    ) {
        this.incidentService = incidentService;
    }

    @PostMapping
    public ResponseEntity<incidentResponse> createIncident(
            @Valid @RequestBody incidentCreateRequest request
    ) {
        incident incident = incidentService.createIncident(
                request.serviceId(),
                request.title(),
                request.severity()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(incidentResponse.fromEntity(incident));
    }

    @GetMapping
    public ResponseEntity<List<incidentResponse>> getAllIncidents() {
        List<incidentResponse> response =
                incidentService.getAllIncidents()
                        .stream()
                        .map(incidentResponse::fromEntity)
                        .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<incidentResponse> getIncidentById(
            @PathVariable UUID id
    ) {
        incident incident = incidentService.getIncidentById(id);

        return ResponseEntity.ok(
                incidentResponse.fromEntity(incident)
        );
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<incidentResponse>> getByStatus(
            @PathVariable incidentStatus status
    ) {
        List<incidentResponse> response =
                incidentService.getIncidentsByStatus(status)
                        .stream()
                        .map(incidentResponse::fromEntity)
                        .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/severity/{severity}")
    public ResponseEntity<List<incidentResponse>> getBySeverity(
            @PathVariable incidentSeverity severity
    ) {
        List<incidentResponse> response =
                incidentService.getIncidentsBySeverity(severity)
                        .stream()
                        .map(incidentResponse::fromEntity)
                        .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/service/{serviceId}")
    public ResponseEntity<List<incidentResponse>> getByService(
            @PathVariable UUID serviceId
    ) {
        List<incidentResponse> response =
                incidentService.getIncidentsByService(serviceId)
                        .stream()
                        .map(incidentResponse::fromEntity)
                        .toList();

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/acknowledge")
    public ResponseEntity<incidentResponse> acknowledgeIncident(
            @PathVariable UUID id
    ) {
        incident incident = incidentService.acknowledgeIncident(id);

        return ResponseEntity.ok(
                incidentResponse.fromEntity(incident)
        );
    }

    @PatchMapping("/{id}/resolve")
    public ResponseEntity<incidentResponse> resolveIncident(
            @PathVariable UUID id
    ) {
        incident incident = incidentService.resolveIncident(id);

        return ResponseEntity.ok(
                incidentResponse.fromEntity(incident)
        );
    }
}