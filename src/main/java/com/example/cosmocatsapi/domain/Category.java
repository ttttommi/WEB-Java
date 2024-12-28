package com.example.cosmocatsapi.domain;

import lombok.Builder;
import lombok.Value;
import lombok.NonNull;
import java.util.UUID;

@Value
@Builder
public class Category {
    UUID id; 

    @NonNull
    String name;

    @NonNull
    String description;
}

