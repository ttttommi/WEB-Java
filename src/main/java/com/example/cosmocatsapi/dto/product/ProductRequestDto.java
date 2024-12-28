package com.example.cosmocatsapi.dto.product;

import com.example.cosmocatsapi.common.ProductStatus;
import com.example.cosmocatsapi.dto.validation.CosmicWordCheck;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Value;
import org.springframework.validation.annotation.Validated;

@Value
@Builder
@Validated 
public class ProductRequestDto {

    @NotBlank(groups = CreateGroup.class, message = "Product name is required")
    @Size(max = 100, groups = CreateGroup.class, message = "Product name must not exceed 100 characters")
    @CosmicWordCheck(groups = CreateGroup.class)
    String name;

    @NotBlank(groups = CreateGroup.class, message = "Product description is required")
    @Size(max = 1000, groups = CreateGroup.class, message = "Product description must not exceed 1000 characters")
    String description;

    @NotNull(groups = CreateGroup.class, message = "Product price is required")
    @Positive(groups = CreateGroup.class, message = "Product price must be positive")
    BigDecimal price;

    @NotNull(groups = CreateGroup.class, message = "Product category is required")
    Integer categoryId;

    ProductStatus productStatus;
}
