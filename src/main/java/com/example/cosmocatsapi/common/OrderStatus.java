package com.example.cosmocatsapi.common;

public enum OrderStatus {
    PENDING("Order is waiting for processing"),
    DELIVERED("Order has been delivered to the customer"),
    COMPLETED("Order has been successfully completed"),
    CANCELED("Order has been canceled"),
    RETURNED("Order has been returned by the customer");

    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
