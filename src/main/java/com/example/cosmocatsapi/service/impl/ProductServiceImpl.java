package com.example.cosmocatsapi.service.impl;

import com.example.cosmocatsapi.common.ProductStatus;
import com.example.cosmocatsapi.domain.Product;
import com.example.cosmocatsapi.dto.product.ProductRequestDto;
import com.example.cosmocatsapi.service.exception.ProductNotFoundException;
import com.example.cosmocatsapi.service.mappers.ProductMapper;
import com.example.cosmocatsapi.dto.product.ProductResponseDto;
import java.math.BigDecimal;
import java.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.cosmocatsapi.service.ProductService;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductMapper productMapper;
    private final List<Product> products = new ArrayList<>();
    private long productIdCounter = 1; 

    @Override
    public ProductResponseDto createProduct(ProductRequestDto productRequestDto) {
        Product newProduct = Product.builder()
                .id(productIdCounter++) 
                .name(productRequestDto.getName())
                .description(productRequestDto.getDescription())
                .price(productRequestDto.getPrice())
                .productStatus(ProductStatus.IN_STOCK)
                .categoryId(productRequestDto.getCategoryId())
                .build();
        products.add(newProduct);
        return productMapper.toProductResponseDto(newProduct);
    }

    @Override
    public List<ProductResponseDto> getAllProducts() {
        return Collections.unmodifiableList(
                products.stream()
                        .map(productMapper::toProductResponseDto)
                        .toList());
    }

    @Override
    public ProductResponseDto getProductById(Long id) {
        Product product = findProductById(id).orElseThrow(() -> 
            new ProductNotFoundException(String.format("Product with ID: %d does not exist.", id))
        );
        return productMapper.toProductResponseDto(product);
    }
    
    @Override
    public ProductResponseDto updateProduct(Long id, ProductRequestDto productRequestDto) {
        Product productToUpdate = findProductById(id).orElseThrow(() -> 
            new ProductNotFoundException(String.format("Product with ID: %d not found for update.", id))
        );
    
        if (productRequestDto.getName() != null) productToUpdate.setName(productRequestDto.getName());
        if (productRequestDto.getDescription() != null) productToUpdate.setDescription(productRequestDto.getDescription());
        if (productRequestDto.getPrice() != null) productToUpdate.setPrice(productRequestDto.getPrice());
        if (productRequestDto.getProductStatus() != null) productToUpdate.setProductStatus(productRequestDto.getProductStatus());
        if (productRequestDto.getCategoryId() != null) productToUpdate.setCategoryId(productRequestDto.getCategoryId());
    
        return productMapper.toProductResponseDto(productToUpdate);
    }
    
    @Override
    public void deleteProduct(Long id) {
        Product productToDelete = findProductById(id).orElseThrow(() -> 
            new ProductNotFoundException("Product with ID: " + id + " cannot be deleted as it was not found.")
        );
        products.remove(productToDelete);
    }

    private Optional<Product> findProductById(Long id) {
        return products.stream()
                .filter(p -> p.getId() == id) 
                .findFirst();
    }
}