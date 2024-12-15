package com.example.cosmocatsapi.service.exception;

import java.io.Serializable;

public class ProductNotFoundException extends RuntimeException implements Serializable {
    private final String productId; 

    public ProductNotFoundException(String productId, String message) {
        super(message);
        this.productId = productId;
    }

    public ProductNotFoundException(String productId, String message, Throwable cause) {
        super(message, cause);
        this.productId = productId;
    }


    public String getProductId() {
        return productId;
    }
}