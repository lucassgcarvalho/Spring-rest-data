package com.project.config;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.RollbackException;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(TransactionSystemException.class)
    protected ResponseEntity<Map<Path, String>> handleTransactionException(TransactionSystemException ex) throws Throwable {

        Throwable cause = ex.getCause();
        if (!(cause instanceof RollbackException)) throw cause;
        if (!(cause.getCause() instanceof ConstraintViolationException)) throw cause.getCause();

        ConstraintViolationException validationException = (ConstraintViolationException) cause.getCause();

        Map<Path, String> collect = validationException.getConstraintViolations()
                .stream()
                .collect(Collectors
                        .toMap(
                                constraintViolation -> constraintViolation.getPropertyPath(),
                                constraintViolation -> constraintViolation.getMessage(),
                                (o, o2) -> String.format("%s, %s", o, o2)));

        return new ResponseEntity<>(collect, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<String> handleConstraintViolationException(DataIntegrityViolationException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }


    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Map<Path, String>> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<Path, String> collect = ex.getConstraintViolations()
                .stream()
                .collect(Collectors
                        .toMap(
                                constraintViolation -> constraintViolation.getPropertyPath(),
                                constraintViolation -> constraintViolation.getMessage(),
                                (o, o2) -> String.format("%s, %s", o, o2)));

        return new ResponseEntity<>(collect, HttpStatus.BAD_REQUEST);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return super.handleMethodArgumentNotValid(ex, headers, status, request);
    }

}