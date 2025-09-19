package com.wma.gestorFinanceiro.api.controller;

import com.wma.gestorFinanceiro.api.dto.category.CategoryRequest;
import com.wma.gestorFinanceiro.api.dto.category.CategoryResponse;
import com.wma.gestorFinanceiro.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody @Valid CategoryRequest request, Principal principal) {
        CategoryResponse newCategory = categoryService.createCategory(request, principal.getName());
        return new ResponseEntity<>(newCategory, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getUserCategories(Principal principal) {
        List<CategoryResponse> categories = categoryService.findCategoriesByUser(principal.getName());
        return ResponseEntity.ok(categories);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable("id") Long categoryId,
                                                           @RequestBody @Valid CategoryRequest request,
                                                           Principal principal) {
        CategoryResponse updatedCategory = categoryService.updateCategory(categoryId, request, principal.getName());
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("id") Long categoryId, Principal principal) {
        categoryService.deleteCategory(categoryId, principal.getName());
        return ResponseEntity.noContent().build();
    }
}
