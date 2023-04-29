package com.spring.example.springuser_department.exceptions;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.time.Instant;

@ControllerAdvice
public class ControllersExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<StandardError> entityNotFound(EntityNotFoundException e,
        HttpServletRequest request) {
        StandardError standardError = getStandardError(HttpStatus.NOT_FOUND, e, request);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(standardError);
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<StandardError> entityExists(EntityExistsException e, HttpServletRequest request) {
        StandardError standardError = getStandardError(HttpStatus.CONFLICT, e, request);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(standardError);
    }

    private StandardError getStandardError(HttpStatus httpStatus, Exception e,
        HttpServletRequest request) {
        StandardError standardError = new StandardError();
        standardError.setTimestamp(Instant.now());
        standardError.setStatus(httpStatus.value());
        standardError.setError(e.getMessage());
        standardError.setPath(request.getRequestURI());
        return standardError;
    }
}