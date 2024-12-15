package com.example.cosmocatsapi.web.utils;

import com.example.cosmocatsapi.web.exception.ExDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import lombok.Component;
import java.util.List;
import java.util.Map;

@Component
public class DetailsUtils {
    public static ProblemDetail getValidationErrors(List<ExDetails> validationErrors) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Request validation failed");

        problemDetail.setTitle("Field Validation Exception");
        problemDetail.setType(getErrorType(validationErrors));
        problemDetail.setProperties(Map.of("invalidParams", validationErrors));

        return problemDetail;
    }

    private static String getErrorType(List<ExDetails> validationErrors) {
        return validationErrors.isEmpty() ? "no-errors" : "field-validation-error";
    }
}