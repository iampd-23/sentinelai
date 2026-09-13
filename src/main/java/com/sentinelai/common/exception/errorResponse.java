package com.sentinelai.common.exception;

import java.time.Instant;
import java.util.Map;

public record errorResponse(
        String code,
        String message,
        Instant timestamp,
        Map<String, String> errors
) {
}