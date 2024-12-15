package com.example.cosmocatsapi.dto.order;

import com.example.cosmocatsapi.common.OrderStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.List;
import lombok.Value;

@Value
public class OrderRequestDto {

    @NotNull(message = "The list of order items must not be null.") 
    List<OrderItemDto> orderItems;

    @NotNull(message = "Shipping address is required.") 
    String address;

    @Email(message = "Please provide a valid email address.") 
    String email;

    @NotNull(message = "Phone number is a required field.") 
    @Pattern(regexp = "^\\+?[0-9]{10,13}$", message = "Phone number should be in correct format.") 
    String phone;

    @NotNull(message = "Total price must be specified.") 
    BigDecimal totalPrice;

    @NotNull(message = "Order status is required.") 
    OrderStatus status;
}
