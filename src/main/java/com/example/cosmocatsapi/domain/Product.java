package com.example.cosmocatsapi.domain;

import com.example.cosmocatsapi.common.ProductStatus;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

@Value
@Builder
public class Product {
    long id;
    String name;
    String description;
    BigDecimal price;
    ProductStatus productStatus;
    long categoryId;
}