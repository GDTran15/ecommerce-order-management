package com.duong.ecommerce.exception;

import com.duong.ecommerce.product.ResourceAlreadyExistedException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String,String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(
                error -> errors.put(error.getField(), error.getDefaultMessage())
        );
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Validation");
        problemDetail.setProperty("error",errors);
        return ResponseEntity.badRequest().body(problemDetail);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleResourceNotFoundException(ResourceNotFoundException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        problemDetail.setTitle("Resource not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail);
    }


    @ExceptionHandler(UserAlreadyExistedException.class)
    public ResponseEntity<ProblemDetail> handleUserAlreadyExistedException(UserAlreadyExistedException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, e.getMessage());
        problemDetail.setTitle("Resource is existed");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(problemDetail);
    }

    @ExceptionHandler(ResourceAlreadyExistedException.class)
    public ResponseEntity<ProblemDetail> handleResourceAlreadyExistedException(ResourceAlreadyExistedException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, e.getMessage());
        problemDetail.setTitle("Resource is existed");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(problemDetail);
    }
    @ExceptionHandler(OutOfStockException.class)
    public ResponseEntity<ProblemDetail> handleOutStockException(OutOfStockException e){
        ProblemDetail problemDetail =ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,e.getMessage());
        problemDetail.setTitle("Out of stock");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(problemDetail);
    }
}
