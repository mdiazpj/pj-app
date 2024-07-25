package com.papajohns.pj_app.Controllers;

import com.papajohns.pj_app.Models.DTO.CreateDTO.CategoryCreateDTO;
import com.papajohns.pj_app.Models.DTO.CreateDTO.CategoryUpdateDTO;
import com.papajohns.pj_app.Models.DTO.ResponseDTO.CategoryResponseDTO;
import com.papajohns.pj_app.Services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/{storeId}/save")
    public ResponseEntity<CategoryResponseDTO> createCategory(@PathVariable UUID storeId, @RequestBody CategoryCreateDTO categoryRequestDTO) {
        CategoryResponseDTO createdCategory = categoryService.createCategory(storeId, categoryRequestDTO);
        return createdCategory != null ? ResponseEntity.ok().body(createdCategory) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> updateCategory(@PathVariable UUID id, @RequestBody CategoryUpdateDTO categoryUpdateDTO) {
        CategoryResponseDTO updatedCategory = categoryService.updateCategory(id, categoryUpdateDTO);
        return updatedCategory != null ? ResponseEntity.ok().body(updatedCategory) : ResponseEntity.notFound().build();
    }

    @GetMapping("/store/{storeId}")
    public List<CategoryResponseDTO> getCategoriesByStoreId(@PathVariable UUID storeId) {
        return categoryService.getCategoriesByStoreId(storeId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> getCategoryById(@PathVariable UUID id) {
        CategoryResponseDTO category = categoryService.getCategoryById(id);
        return category != null ? ResponseEntity.ok().body(category) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable UUID id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}