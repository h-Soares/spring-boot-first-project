package com.spring.example.springuser_department.exceptions;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.time.Instant;
import java.util.List;

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

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<StandardError> illegalArgument(IllegalArgumentException e, HttpServletRequest request) {
        StandardError standardError = getStandardError(HttpStatus.BAD_REQUEST, e, request);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(standardError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class) //thrown by insert DTO's
    public ResponseEntity<StandardInsertDTOError> methodArgumentNotValid(MethodArgumentNotValidException e,
        HttpServletRequest request) {
        List<String> errors = e.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .sorted().toList();
        StandardInsertDTOError insertDTOError = getStandardInsertDTOError(HttpStatus.BAD_REQUEST, e,
                request, errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(insertDTOError);
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

    private StandardInsertDTOError getStandardInsertDTOError(HttpStatus httpStatus, Exception e,
            HttpServletRequest request, List<String> errors) {
        StandardInsertDTOError insertDTOError = new StandardInsertDTOError();
        insertDTOError.setTimestamp(Instant.now());
        insertDTOError.setStatus(httpStatus.value());
        insertDTOError.setErrors(errors);
        insertDTOError.setPath(request.getRequestURI());
        return insertDTOError;
    }
}