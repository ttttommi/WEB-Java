package com.example.cosmocatsapi.dto.order;

import com.example.cosmocatsapi.common.OrderStatus;
import com.example.cosmocatsapi.domain.OrderItem;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Value
@Builder
@Jacksonized
public class OrderResponseDto {
    UUID id;
    String name;
    String address;
    String phone;
    String email;
    OrderStatus status;
    BigDecimal totalPrice;
    List<OrderItem> orderItems;
}
