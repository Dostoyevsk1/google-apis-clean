package com.trakto.googlemapsclean.interfaces.rest.exception;

import com.trakto.googlemapsclean.application.exception.LocationNotFoundException;
import com.trakto.googlemapsclean.domain.exception.UpstreamLocationException;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // 400: falló @Valid en un request body
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidation(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(f -> f.getField() + ": " + f.getDefaultMessage())
                .findFirst()
                .orElse("Validation failed");
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, message);
    }

    // 400: GeoPoint fuera de rango o entrada vacía en un query service
    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handleIllegalArgument(IllegalArgumentException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    // 404
    @ExceptionHandler(LocationNotFoundException.class)
    public ProblemDetail handleNotFound(LocationNotFoundException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
    }

    // 502: falló Google (el detalle va al log, no al cliente)
    @ExceptionHandler(UpstreamLocationException.class)
    public ProblemDetail handleUpstream(UpstreamLocationException e) {
        log.error("Upstream location provider failed", e);
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_GATEWAY, "Location provider is unavailable");
    }

    // 500: cualquier otro error inesperado (un bug nuestro)
    @ExceptionHandler(Exception.class)
    public ProblemDetail handleUnexpected(Exception e) {
        log.error("Unexpected error", e);
        return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error");
    }
}