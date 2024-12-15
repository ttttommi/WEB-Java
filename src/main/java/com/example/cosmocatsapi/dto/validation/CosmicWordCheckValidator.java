package com.example.cosmocatsapi.dto.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class CosmicWordCheckValidator implements ConstraintValidator<CosmicWordCheck, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        String validPattern = "(?i)\\b(star|galaxy|comet)\\b";
        Pattern pattern = Pattern.compile(validPattern);

        boolean isValid = true;
        for (String word : new String[]{"star", "galaxy", "comet"}) {
            if (!pattern.matcher(value).find()) {
                isValid = false;
                break;
            }
        }
        return isValid;
    }
}
