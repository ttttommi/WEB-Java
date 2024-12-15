package com.example.cosmocatsapi.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.example.cosmocatsapi.common.ProductStatus;
import com.example.cosmocatsapi.domain.Product;
import com.example.cosmocatsapi.dto.product.ProductRequestDto;
import com.example.cosmocatsapi.dto.product.ProductResponseDto;
import com.example.cosmocatsapi.service.exception.ProductNotFoundException;
import com.example.cosmocatsapi.service.mappers.ProductMapper;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    @DisplayName("Ensure that createProduct() correctly creates a product and returns the corresponding response")
    public void createProduct_ValidProductRequestDto_ReturnsProductResponseDto() {
        ProductRequestDto productRequestDto = getRequestProductDto();
        ProductResponseDto expectedResponse = getProductResponseDto();

        when(productMapper.toProductResponseDto(any())).thenReturn(expectedResponse);

        ProductResponseDto savedProduct = productService.createProduct(productRequestDto);

        assertThat(savedProduct).isEqualTo(expectedResponse);
        verify(productMapper, times(1)).toProductResponseDto(any());
        verifyNoMoreInteractions(productMapper);
    }

    @Test
    @DisplayName("Confirm that getAllProducts() correctly returns a list of products")
    public void getAllProducts_ShouldReturnProductList() {
        Product product1 = Product.builder().id(1).name("Product 1").description("Description 1")
                .price(BigDecimal.valueOf(5)).productStatus(ProductStatus.IN_STOCK).categoryId(1).build();
        Product product2 = Product.builder().id(2).name("Product 2").description("Description 2")
                .price(BigDecimal.valueOf(10)).productStatus(ProductStatus.DISCONTINUED).categoryId(2).build();

        ProductResponseDto responseDto1 = ProductResponseDto.builder().id(1).name("Product 1")
                .description("Description 1").price(BigDecimal.valueOf(5)).productStatus(ProductStatus.IN_STOCK).categoryId(1).build();
        ProductResponseDto responseDto2 = ProductResponseDto.builder().id(2).name("Product 2")
                .description("Description 2").price(BigDecimal.valueOf(10)).productStatus(ProductStatus.DISCONTINUED).categoryId(2).build();

        when(productMapper.toProductResponseDto(product1)).thenReturn(responseDto1);
        when(productMapper.toProductResponseDto(product2)).thenReturn(responseDto2);

        List<ProductResponseDto> result = productService.getAllProducts();

        assertNotNull(result);
        assertThat(result).hasSizeGreaterThanOrEqualTo(2);
        assertEquals("Product 1", result.get(0).getName());
        assertEquals("Product 2", result.get(1).getName());
        verify(productMapper, times(2)).toProductResponseDto(any(Product.class));
    }

    @Test
    @DisplayName("Check that getProductById() correctly retrieves the product")
    public void getProductById_ValidId_ShouldReturnProduct() {
        Long validProductId = 1L;
        Product product = Product.builder().id(validProductId).name("Product 1").description("Description 1")
                .price(BigDecimal.valueOf(5)).productStatus(ProductStatus.IN_STOCK).categoryId(1).build();
        ProductResponseDto expectedResponse = ProductResponseDto.builder().id(validProductId).name("Product 1")
                .description("Description 1").price(BigDecimal.valueOf(5)).productStatus(ProductStatus.IN_STOCK).categoryId(1).build();

        when(productMapper.toProductResponseDto(product)).thenReturn(expectedResponse);

        ProductResponseDto result = productService.getProductById(validProductId);

        assertNotNull(result);
        assertEquals(validProductId, result.getId());
        assertEquals("Product 1", result.getName());
        verify(productMapper, times(1)).toProductResponseDto(product);
    }

    @Test
    @DisplayName("Ensure that getProductById() throws an exception if the product is not found")
    public void getProductById_InvalidId_ShouldThrowException() {
        Long invalidProductId = 999L;

        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(invalidProductId), "Product does not exist");
    }

    @Test
    @DisplayName("Ensure that updateProduct() correctly updates and returns the product")
    public void updateProduct_ValidIdAndRequest_ShouldReturnUpdatedProduct() {
        Long productId = 1L;
        ProductRequestDto requestDto = ProductRequestDto.builder().name("Updated Name")
                .description("Updated Description").price(BigDecimal.valueOf(15))
                .productStatus(ProductStatus.DISCONTINUED).categoryId(2).build();
        Product updatedProduct = Product.builder().name("Updated Name")
                .description("Updated Description").price(BigDecimal.valueOf(15))
                .productStatus(ProductStatus.DISCONTINUED).categoryId(2).build();
        ProductResponseDto expectedResponse = ProductResponseDto.builder().id(1).name("Updated Name")
                .description("Updated Description").price(BigDecimal.valueOf(15))
                .productStatus(ProductStatus.DISCONTINUED).categoryId(2).build();

        when(productMapper.toProductResponseDto(any())).thenReturn(expectedResponse);

        ProductResponseDto result = productService.updateProduct(productId, requestDto);

        assertNotNull(result);
        assertThat(result.getName()).isEqualTo("Updated Name");
        assertThat(result.getPrice()).isEqualByComparingTo(BigDecimal.valueOf(15));
        assertThat(result.getProductStatus()).isEqualTo(ProductStatus.DISCONTINUED);
        assertThat(result.getDescription()).isEqualTo("Updated Description");
        verify(productMapper, times(1)).toProductResponseDto(any());
    }

    @Test
    @DisplayName("Ensure that deleteProduct() removes the specified product")
    public void deleteProduct_ValidId_ShouldRemoveProduct() {
        Long productId = 1L;

        productService.deleteProduct(productId);

        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(productId));
        verify(productMapper, times(1)).toProductResponseDto(any());
    }

    private ProductRequestDto getRequestProductDto() {
        return ProductRequestDto.builder()
                .name("Test")
                .description("Test")
                .price(BigDecimal.valueOf(5))
                .productStatus(ProductStatus.IN_STOCK)
                .categoryId(1)
                .build();
    }

    private ProductResponseDto getProductResponseDto() {
        return ProductResponseDto.builder().id(9999999)
                .name("Test")
                .description("Test")
                .price(BigDecimal.valueOf(5))
                .productStatus(ProductStatus.IN_STOCK)
                .categoryId(1)
                .build();
    }
}