package com.sentinelai.evidence.controller;

import com.sentinelai.evidence.dto.incidentEvidenceResponse;
import com.sentinelai.evidence.service.incidentEvidenceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/incidents")
public class incidentEvidenceController {

    private final incidentEvidenceService incidentEvidenceService;

    public incidentEvidenceController(
            incidentEvidenceService incidentEvidenceService) {

        this.incidentEvidenceService = incidentEvidenceService;
    }

    @GetMapping("/{incidentId}/evidence")
    public List<incidentEvidenceResponse> getEvidence(
            @PathVariable UUID incidentId) {

        return incidentEvidenceService.getEvidenceForIncident(
                incidentId);
    }
}