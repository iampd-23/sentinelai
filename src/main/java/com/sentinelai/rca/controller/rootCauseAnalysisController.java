package com.sentinelai.rca.controller;

import com.sentinelai.rca.dto.rootCauseCandidateResponse;
import com.sentinelai.rca.entity.rootCauseCandidate;
import com.sentinelai.rca.service.rootCauseAnalysisService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/incidents")
public class rootCauseAnalysisController {

    private final rootCauseAnalysisService rootCauseAnalysisService;

    public rootCauseAnalysisController(
            rootCauseAnalysisService rootCauseAnalysisService) {

        this.rootCauseAnalysisService = rootCauseAnalysisService;
    }

    @PostMapping("/{incidentId}/root-cause/analyze")
    public List<rootCauseCandidateResponse> analyzeIncident(
            @PathVariable UUID incidentId) {

        return rootCauseAnalysisService.analyzeIncident(incidentId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @GetMapping("/{incidentId}/root-cause")
    public List<rootCauseCandidateResponse> getCandidates(
            @PathVariable UUID incidentId) {

        return rootCauseAnalysisService.getCandidates(incidentId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private rootCauseCandidateResponse toResponse(
            rootCauseCandidate candidate) {

        return new rootCauseCandidateResponse(
                candidate.getId(),
                candidate.getIncident().getId(),
                candidate.getCandidateService().getId(),
                candidate.getCandidateService().getName(),
                candidate.getReason(),
                candidate.getConfidence());
    }
}