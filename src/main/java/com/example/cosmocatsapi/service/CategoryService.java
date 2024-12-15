package com.example.cosmocatsapi.service;

import com.example.cosmocatsapi.dto.category.CategoryDto;
import java.util.List;
import java.util.Optional;

public interface CategoryService {
    CategoryDto createCategory(CategoryDto categoryDto);
    
    CategoryDto updateCategory(CategoryDto categoryDto);

    Optional<CategoryDto> getCategoryById(int id);

    List<CategoryDto> getAllCategories();

    boolean deleteCategoryById(int id);
}