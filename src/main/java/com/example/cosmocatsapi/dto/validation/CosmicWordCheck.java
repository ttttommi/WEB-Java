package com.example.cosmocatsapi.dto.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CosmicWordCheckValidator.class)
public @interface CosmicWordCheck {
    String MESSAGE = "The input contains invalid cosmic words. Please use words like 'star', 'galaxy', or 'comet'.";

    String message() default MESSAGE;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
