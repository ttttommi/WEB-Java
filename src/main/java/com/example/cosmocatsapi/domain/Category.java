package com.example.cosmocatsapi.domain;

import lombok.Builder;
import lombok.Value;
import lombok.NonNull;

@Value
@Builder
public class Category {
    long id;

    @NonNull
    String name;

    @NonNull
    String description;
}
