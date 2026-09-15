package com.sentinelai.evidence.controller;

import com.sentinelai.evidence.entity.incidentErrorGroup;
import com.sentinelai.evidence.service.incidentErrorGroupService;
import org.springframework.web.bind.annotation.*;
import com.sentinelai.evidence.dto.incidentErrorGroupResponse;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/incidents")
public class incidentErrorGroupController {

    private final incidentErrorGroupService incidentErrorGroupService;

    public incidentErrorGroupController(
            incidentErrorGroupService incidentErrorGroupService) {

        this.incidentErrorGroupService = incidentErrorGroupService;
    }

    @GetMapping("/{incidentId}/error-groups")
    public List<incidentErrorGroupResponse> getErrorGroups(
            @PathVariable UUID incidentId) {

        return incidentErrorGroupService.getErrorGroupsForIncident(
                incidentId);
    }
}