package org.airtribe.LearnerManagementSystemBelC22.controller;

import java.util.HashMap;
import java.util.Map;
import org.airtribe.LearnerManagementSystemBelC22.exception.CohortNotFoundException;
import org.airtribe.LearnerManagementSystemBelC22.exception.LearnerNotFoundException;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class CentralExceptionHandler {
  @ExceptionHandler(LearnerNotFoundException.class)
  public ResponseEntity handleLearnerNotFoundException(LearnerNotFoundException exception) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
  }

  @ExceptionHandler(CohortNotFoundException.class)
  public ResponseEntity handleCohortNotFoundException(CohortNotFoundException exception) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
  }

  @ExceptionHandler(JdbcSQLIntegrityConstraintViolationException.class)
  public ResponseEntity handleJdbcSqlIntegrity(JdbcSQLIntegrityConstraintViolationException exception) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unique constraint violated");
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
    Map<String, String> errors = new HashMap<>();
    exception.getBindingResult().getAllErrors().forEach(error -> {
      String errorMessage = error.getDefaultMessage();
      String fieldName = ((org.springframework.validation.FieldError) error).getField();
      errors.put(fieldName, errorMessage);
    });

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
  }
}
