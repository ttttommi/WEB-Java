package com.example.cosmocatsapi.web;

import com.example.cosmocatsapi.dto.product.ProductRequestDto;
import com.example.cosmocatsapi.dto.product.ProductResponseDto;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.example.cosmocatsapi.service.ProductService;

@RestController
@RequestMapping("/api/v1/products")
@Validated
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        List<ProductResponseDto> products = productService.getAllProducts();
        if (products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable("id") Long id) {
        ProductResponseDto productResponseDto = productService.getProductById(id);
        if (productResponseDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(productResponseDto);
    }

    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(
            @RequestBody @Valid ProductRequestDto productRequestDto) {
        ProductResponseDto createdProduct = productService.createProduct(productRequestDto);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(
            @RequestBody @Valid ProductRequestDto productRequestDto,
            @PathVariable Long id) {
        ProductResponseDto updatedProduct = productService.updateProduct(id, productRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}