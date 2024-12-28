package com.example.cosmocatsapi.web.exception;

import lombok.AllArgsConstructor;
import java.util.Optional;
import lombok.Value;

@Value
@AllArgsConstructor
public class ExDetails {
    private Optional<String> field;
    private Optional<String> reason;
}
