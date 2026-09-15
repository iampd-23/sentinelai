package com.sentinelai.detection.service;

import com.sentinelai.detection.dto.errorGroup;
import com.sentinelai.telemetry.entity.logTelemetry;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class errorGroupingService {

    public List<errorGroup> groupErrors(
            List<logTelemetry> relatedLogs) {

        Map<String, Long> groupedErrors =
                relatedLogs.stream()
                        .collect(Collectors.groupingBy(
                                logTelemetry::getMessage,
                                Collectors.counting()
                        ));

        return groupedErrors.entrySet()
                .stream()
                .map(entry ->
                        new errorGroup(
                                entry.getKey(),
                                entry.getValue()))
                .sorted((first, second) ->
                        Long.compare(
                                second.count(),
                                first.count()))
                .toList();
    }
}