package com.example.cosmocatsapi.dto.order;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Min;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class OrderItemDto {

    @NotNull(message = "Product name is a required field.")
    String product;

    @NotNull(message = "The price of the product cannot be null.")
    @Positive(message = "Price must be a positive value.")
    BigDecimal price;

    @NotNull(message = "Product quantity is required.")
    @Min(value = 1, message = "Quantity must be at least 1.")
    Integer quantity;
}