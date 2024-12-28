package com.example.cosmocatsapi.dto.product;

import com.example.cosmocatsapi.common.ProductStatus;
import java.math.BigDecimal;
import java.util.UUID; 
import lombok.Builder;
import lombok.Value;

@Value 
@Builder
public class ProductResponseDto {
    UUID id; 
    String name;
    String description;
    BigDecimal price;
    ProductStatus productStatus;
    UUID categoryId; 
}