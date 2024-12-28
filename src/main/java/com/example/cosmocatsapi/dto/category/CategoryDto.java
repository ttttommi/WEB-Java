package com.example.cosmocatsapi.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CategoryDto {

    @NotBlank(message = "Please provide a valid category name.")
    @Size(min = 1, max = 100, message = "Category name must be between 1 and 100 characters.")
    String name;

    @NotBlank(message = "Category description is required.")
    @Size(min = 5, max = 1000, message = "Category description should be between 5 and 1000 characters long.")
    String description;
}
