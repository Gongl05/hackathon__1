package com.oreo.insightfactory.common;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
    String msg = ex.getBindingResult().getAllErrors().stream()
      .findFirst().map(e -> e.getDefaultMessage()).orElse("Validation error");
    return ResponseEntity.badRequest().body(new ApiError("BAD_REQUEST", msg, Instant.now(), req.getRequestURI()));
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ApiError> handleIllegal(IllegalArgumentException ex, HttpServletRequest req) {
    return ResponseEntity.badRequest().body(new ApiError("BAD_REQUEST", ex.getMessage(), Instant.now(), req.getRequestURI()));
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<ApiError> handleConflict(DataIntegrityViolationException ex, HttpServletRequest req) {
    return ResponseEntity.status(HttpStatus.CONFLICT)
      .body(new ApiError("CONFLICT", "username o email ya existe", Instant.now(), req.getRequestURI()));
  }
}