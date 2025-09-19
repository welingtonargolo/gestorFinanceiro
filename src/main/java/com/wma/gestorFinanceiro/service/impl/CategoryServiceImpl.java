package com.wma.gestorFinanceiro.service.impl;

import com.wma.gestorFinanceiro.api.dto.category.CategoryRequest;
import com.wma.gestorFinanceiro.api.dto.category.CategoryResponse;
import com.wma.gestorFinanceiro.domain.entity.Category;
import com.wma.gestorFinanceiro.domain.entity.User;
import com.wma.gestorFinanceiro.domain.repository.CategoryRepository;
import com.wma.gestorFinanceiro.domain.repository.UserRepository;
import com.wma.gestorFinanceiro.exception.ResourceNotFoundException;
import com.wma.gestorFinanceiro.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public CategoryResponse createCategory(CategoryRequest request, String userEmail) {
        User user = findUserByEmail(userEmail);

        Category newCategory = Category.builder()
                .name(request.name())
                .user(user)
                .build();

        Category savedCategory = categoryRepository.save(newCategory);

        return toCategoryResponse(savedCategory);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> findCategoriesByUser(String userEmail) {
        User user = findUserByEmail(userEmail);
        return categoryRepository.findByUserId(user.getId())
                .stream()
                .map(this::toCategoryResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CategoryResponse updateCategory(Long categoryId, CategoryRequest request, String userEmail) {
        User user = findUserByEmail(userEmail);
        Category category = findCategoryByIdAndUser(categoryId, user.getId());

        category.setName(request.name());
        Category updatedCategory = categoryRepository.save(category);

        return toCategoryResponse(updatedCategory);
    }

    @Override
    @Transactional
    public void deleteCategory(Long categoryId, String userEmail) {
        User user = findUserByEmail(userEmail);
        Category category = findCategoryByIdAndUser(categoryId, user.getId());
        categoryRepository.delete(category);
    }

    /**
     * Busca um utilizador pelo seu e-mail ou lança uma exceção se não for encontrado.
     */
    private User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilizador não encontrado com o e-mail: " + email));
    }

    /**
     * Busca uma categoria pelo ID e verifica se pertence ao utilizador.
     * Lança uma exceção se não for encontrada ou não pertencer ao utilizador.
     */
    private Category findCategoryByIdAndUser(Long categoryId, Long userId) {
        return categoryRepository.findById(categoryId)
                .filter(category -> category.getUser().getId().equals(userId))
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada ou não pertence ao utilizador."));
    }

    /**
     * Converte uma entidade Category para um DTO CategoryResponse.
     */
    private CategoryResponse toCategoryResponse(Category category) {
        return new CategoryResponse(category.getId(), category.getName());
    }
}
