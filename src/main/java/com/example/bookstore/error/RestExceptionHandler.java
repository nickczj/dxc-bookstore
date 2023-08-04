package com.example.bookstore.error;

import jakarta.persistence.EntityNotFoundException;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
    return new ResponseEntity<>(apiError, apiError.getStatus());
  }
  @ExceptionHandler(EntityNotFoundException.class)
  protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
    ApiError apiError = new ApiError(NOT_FOUND);
    apiError.setMessage(ex.getMessage());
    return buildResponseEntity(apiError);
  }

  @ExceptionHandler(JdbcSQLIntegrityConstraintViolationException.class)
  protected ResponseEntity<Object> handleConstraintViolation(JdbcSQLIntegrityConstraintViolationException ex) {
    ApiError apiError;
    if ("23505".equals(ex.getSQLState())) {
      apiError = new ApiError(BAD_REQUEST);
    } else {
      apiError = new ApiError(INTERNAL_SERVER_ERROR);
    }
    apiError.setMessage(ex.getMessage());
    return buildResponseEntity(apiError);
  }
}