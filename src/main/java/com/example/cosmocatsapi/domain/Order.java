package com.example.cosmocatsapi.domain;

import com.example.cosmocatsapi.common.OrderStatus;
import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Order {
    String name;
    String address;
    String phone;
    String email;
    OrderStatus status;
    List<OrderItem> orderItems;

    public void updateStatus(OrderStatus newStatus) {
        this.status = newStatus;
    }
}