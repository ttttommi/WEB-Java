package com.example.cosmocatsapi.web;

import com.example.cosmocatsapi.service.exception.ProductNotFoundException;
import com.example.cosmocatsapi.web.exception.ExDetails;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleProductNotFoundException(ProductNotFoundException ex) {
        ProblemDetail problemDetail = new ProblemDetail();
        problemDetail.setStatus(HttpStatus.NOT_FOUND.value());
        problemDetail.setDetail(ex.getMessage());
        problemDetail.setType(URI.create("product-not-found"));
        problemDetail.setTitle("Product Not Found");

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        
        List<ExDetails> validationResponse = fieldErrors.stream()
                .map(error -> new ExDetails(error.getField(), error.getDefaultMessage()))
                .toList();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationResponse);
    }
}