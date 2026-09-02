package com.example.iaccs.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.sql.SQLException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleEnumParsingException(
            HttpMessageNotReadableException ex) {

        Throwable cause = ex.getMostSpecificCause();

        if (cause instanceof InvalidFormatException invalidFormatException
                && invalidFormatException.getTargetType().isEnum()) {

            Class<?> enumClass = invalidFormatException.getTargetType();

            Object[] allowedValues = enumClass.getEnumConstants();

            Map<String, Object> response = new HashMap<>();
            response.put("error", "Invalid value for " + enumClass.getSimpleName());
            response.put("received", invalidFormatException.getValue());
            response.put("allowedValues", allowedValues);

            return ResponseEntity.badRequest().body(response);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("error", "Malformed request body");

        return ResponseEntity.badRequest().body(response);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String,Object>> handleIllegalArgumentException(
            IllegalArgumentException ex) {

        Map<String,Object> response=new HashMap<>();
                response.put("timestamp", Instant.now());
                response.put("status",HttpStatus.BAD_REQUEST.value());
                response.put("error","Bad Request");
                response.put("message",ex.getMessage());

        return ResponseEntity.badRequest().body(response);
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String,Object>> handleAccessDenied(
            AccessDeniedException exception){

        Map<String,Object> response=new HashMap<>();
        response.put("timestamp", Instant.now());
        response.put("status",HttpStatus.FORBIDDEN.value());
        response.put("error","Forbidden");
        response.put("message",exception.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<String> handleDuplicate(DuplicateResourceException ex) {

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ex.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDuplicate(
            DataIntegrityViolationException ex) {

        Throwable cause = ex.getCause();

        if (cause instanceof SQLException sqlEx) {

            switch (sqlEx.getSQLState()) {

                case "23505":
                    return ResponseEntity.status(HttpStatus.CONFLICT)
                            .body("Duplicate resource.");

                case "23503":
                    return ResponseEntity.status(HttpStatus.CONFLICT)
                            .body("Cannot delete resource because it is referenced by other records.");
            }
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Database integrity violation.");
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleNotFound(
            ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
