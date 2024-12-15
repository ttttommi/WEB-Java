package com.example.cosmocatsapi.service;

import com.example.cosmocatsapi.dto.order.OrderRequestDto;
import com.example.cosmocatsapi.dto.order.OrderResponseDto;
import java.util.List;
import java.util.Optional;

public interface OrderService {
    OrderResponseDto addOrder(OrderRequestDto orderRequestDto);

    Optional<OrderResponseDto> getOrder(String orderId);

    List<OrderResponseDto> getAllOrders(String status); 
}
