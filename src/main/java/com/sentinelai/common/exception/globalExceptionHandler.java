package com.sentinelai.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class globalExceptionHandler {

    @ExceptionHandler(serviceAlreadyExistsException.class)
    public ResponseEntity<errorResponse> handleServiceAlreadyExists(
            serviceAlreadyExistsException exception
    ) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new errorResponse(
                        "SERVICE_ALREADY_EXISTS",
                        exception.getMessage(),
                        Instant.now(),
                        null
                ));
    }

    @ExceptionHandler(serviceNotFoundException.class)
    public ResponseEntity<errorResponse> handleServiceNotFound(
            serviceNotFoundException exception
    ) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new errorResponse(
                        "SERVICE_NOT_FOUND",
                        exception.getMessage(),
                        Instant.now(),
                        null
                ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<errorResponse> handleValidationException(
            MethodArgumentNotValidException exception
    ) {
        Map<String, String> errors = new LinkedHashMap<>();

        exception.getBindingResult()
                .getFieldErrors()
                .forEach(fieldError ->
                        errors.put(
                                fieldError.getField(),
                                fieldError.getDefaultMessage()
                        )
                );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new errorResponse(
                        "VALIDATION_FAILED",
                        "Request validation failed",
                        Instant.now(),
                        errors
                ));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<errorResponse> handleTypeMismatch(
            MethodArgumentTypeMismatchException exception
    ) {
        String message =
                "Invalid value for parameter: " + exception.getName();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new errorResponse(
                        "INVALID_REQUEST",
                        message,
                        Instant.now(),
                        null
                ));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<errorResponse> handleUnreadableRequest(
            HttpMessageNotReadableException exception
    ) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new errorResponse(
                        "INVALID_REQUEST",
                        "Request body is invalid or contains an unsupported value",
                        Instant.now(),
                        null
                ));
    }
}