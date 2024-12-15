package com.example.cosmocatsapi.web;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
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
import com.example.cosmocatsapi.service.impl.ProductServiceImpl;
import com.example.cosmocatsapi.service.mappers.ProductMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private ProductMapper productMapper;

    @MockBean
    private ProductServiceImpl productService;

    @Test
    @DisplayName("Retrieve all products")
    void getAllProducts_ShouldReturnAllProducts() throws Exception {
        List<ProductResponseDto> expected = new ArrayList<>(Arrays.asList(
                ProductResponseDto.builder()
                        .id(1)
                        .name("Product 1")
                        .description("Description 1")
                        .price(BigDecimal.valueOf(5))
                        .productStatus(ProductStatus.IN_STOCK)
                        .categoryId(1)
                        .build(),
                ProductResponseDto.builder()
                        .id(2)
                        .name("Product 2")
                        .description("Description 2")
                        .price(BigDecimal.TEN)
                        .productStatus(ProductStatus.DISCONTINUED)
                        .categoryId(2)
                        .build(),
                ProductResponseDto.builder()
                        .id(3)
                        .name("Product 3")
                        .description("Description 3")
                        .price(BigDecimal.valueOf(15))
                        .productStatus(ProductStatus.OUT_OF_STOCK)
                        .categoryId(2)
                        .build()
        ));

        when(productService.getAllProducts()).thenReturn(expected);

        MvcResult result = mockMvc.perform(get("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        ProductResponseDto[] actual = objectMapper.readValue(result.getResponse().getContentAsByteArray(),
                ProductResponseDto[].class);

        assertEquals(expected.size(), actual.length);
        assertEquals(expected, Arrays.stream(actual).toList());
    }

    @Test
    @DisplayName("Create a product with valid information")
    void createProduct_ShouldReturnCreatedProduct() throws Exception {
        ProductRequestDto productRequestDto = ProductRequestDto.builder()
                .name("comet")
                .description("Correct Product Description")
                .price(BigDecimal.valueOf(20))
                .categoryId(1)
                .productStatus(ProductStatus.IN_STOCK)
                .build();

        ProductResponseDto productResponseDto = ProductResponseDto.builder()
                .id(1)
                .name("comet")
                .description("Correct Product Description")
                .price(BigDecimal.valueOf(20))
                .categoryId(1)
                .productStatus(ProductStatus.IN_STOCK)
                .build();

        when(productService.createProduct(productRequestDto)).thenReturn(productResponseDto);

        MvcResult result = mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequestDto)))
                .andExpect(status().isCreated())
                .andReturn();

        ProductResponseDto actual = objectMapper.readValue(result.getResponse().getContentAsByteArray(), ProductResponseDto.class);
        assertNotNull(actual.getId());
        assertEquals(productRequestDto.getName(), actual.getName());
    }

    @Test
    @DisplayName("Create a product without a name")
    void createProduct_MissingName_ShouldReturnBadRequest() throws Exception {
        ProductRequestDto productRequestDto = ProductRequestDto.builder()
                .name("")  
                .description("Correct Product Description")
                .price(BigDecimal.valueOf(20))
                .categoryId(1)
                .productStatus(ProductStatus.IN_STOCK)
                .build();

        MvcResult result = mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequestDto)))
                .andExpect(status().isBadRequest())  
                .andReturn();

 
        String responseContent = result.getResponse().getContentAsString();
        assertTrue(responseContent.contains("Product name is required"));
    }

    @Test
    @DisplayName("Create a product with too long name")
    void createProduct_TooLongName_ShouldReturnBadRequest() throws Exception {
        String longName = "A".repeat(101);  
        ProductRequestDto productRequestDto = ProductRequestDto.builder()
                .name(longName)
                .description("Correct Product Description")
                .price(BigDecimal.valueOf(20))
                .categoryId(1)
                .productStatus(ProductStatus.IN_STOCK)
                .build();

        MvcResult result = mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequestDto)))
                .andExpect(status().isBadRequest())  
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        assertTrue(responseContent.contains("Product name must not exceed 100 characters"));
    }

    @Test
    @DisplayName("Create a product without a price")
    void createProduct_MissingPrice_ShouldReturnBadRequest() throws Exception {
        ProductRequestDto productRequestDto = ProductRequestDto.builder()
                .name("Correct Product Name")
                .description("Correct Product Description")
                .price(null)  
                .categoryId(1)
                .productStatus(ProductStatus.IN_STOCK)
                .build();

        MvcResult result = mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequestDto)))
                .andExpect(status().isBadRequest()) 
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        assertTrue(responseContent.contains("Product price is required"));
    }

    @Test
    @DisplayName("Create a product without a categoryId")
    void createProduct_MissingCategoryId_ShouldReturnBadRequest() throws Exception {
        ProductRequestDto productRequestDto = ProductRequestDto.builder()
                .name("Correct Product Name")
                .description("Correct Product Description")
                .price(BigDecimal.valueOf(20))
                .categoryId(null)  
                .productStatus(ProductStatus.IN_STOCK)
                .build();

        MvcResult result = mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequestDto)))
                .andExpect(status().isBadRequest())  
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        assertTrue(responseContent.contains("Product category is required"));
    }

    @Test
    @DisplayName("Update a product with correct data")
    void updateProduct_ShouldReturnUpdatedProduct() throws Exception {
        ProductRequestDto productRequestDto = ProductRequestDto.builder()
                .name("star")
                .description("Modified Product Description")
                .price(BigDecimal.valueOf(25))
                .categoryId(1)
                .productStatus(ProductStatus.OUT_OF_STOCK)
                .build();

        ProductResponseDto productResponseDto = ProductResponseDto.builder()
                .id(1)
                .name("star")
                .description("Modified Product Description")
                .price(BigDecimal.valueOf(25))
                .categoryId(1)
                .productStatus(ProductStatus.OUT_OF_STOCK)
                .build();

        when(productService.updateProduct(1L, productRequestDto)).thenReturn(productResponseDto);

        MvcResult result = mockMvc.perform(put("/api/v1/products/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequestDto)))
                .andExpect(status().isOk())  
                .andReturn();

        ProductResponseDto actual = objectMapper.readValue(result.getResponse().getContentAsByteArray(), ProductResponseDto.class);
        assertEquals(productResponseDto.getId(), actual.getId());
        assertEquals(productResponseDto.getName(), actual.getName());
        assertEquals(productResponseDto.getDescription(), actual.getDescription());
        assertEquals(productResponseDto.getPrice(), actual.getPrice());
    }

    @Test
    @DisplayName("Attempt to update a non-existing product")
    void updateProduct_ProductNotFound_ShouldReturnNotFound() throws Exception {
        Long productId = 99L;  
        ProductRequestDto productRequestDto = ProductRequestDto.builder()
                .name("non-existing")
                .description("Product does not exist")
                .price(BigDecimal.valueOf(30))
                .categoryId(1)
                .productStatus(ProductStatus.OUT_OF_STOCK)
                .build();

        when(productService.updateProduct(productId, productRequestDto))
                .thenThrow(new ProductNotFoundException("Product does not exist"));

        MvcResult result = mockMvc.perform(put("/api/v1/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequestDto)))
                .andExpect(status().isNotFound())  
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        assertTrue(responseContent.contains("Product does not exist"));
    }

    @Test
    @DisplayName("Remove a product")
    void deleteProduct_ShouldReturnNoContent() throws Exception {
        Long productId = 1L;  
        doNothing().when(productService).deleteProduct(productId);

        MvcResult result = mockMvc.perform(delete("/api/v1/products/{id}", productId))
                .andExpect(status().isNoContent())  
                .andReturn();

        assertEquals(204, result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Attempt to update a non-existing product")
    void deleteProduct_ProductNotFound_ShouldReturnNotFound() throws Exception {
        Long productId = 99L; 
        doThrow(new ProductNotFoundException("Product does not exist")).when(productService).deleteProduct(productId);

        MvcResult result = mockMvc.perform(delete("/api/v1/products/{id}", productId))
                .andExpect(status().isNotFound())  
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        assertTrue(responseContent.contains("Product does not exist"));
    }
}
