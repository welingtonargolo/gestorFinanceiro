package com.wma.gestorFinanceiro.service;

import com.wma.gestorFinanceiro.api.dto.category.CategoryRequest;
import com.wma.gestorFinanceiro.api.dto.category.CategoryResponse;

import java.util.List;

/**
 * Interface para o serviço de gestão de categorias.
 * Define o contrato para as operações de CRUD de categorias.
 */
public interface CategoryService {

    /**
     * Cria uma nova categoria para o utilizador autenticado.
     * @param request DTO com os dados da nova categoria.
     * @param userEmail Email do utilizador autenticado.
     * @return DTO com os dados da categoria criada.
     */
    CategoryResponse createCategory(CategoryRequest request, String userEmail);

    /**
     * Lista todas as categorias do utilizador autenticado.
     * @param userEmail Email do utilizador autenticado.
     * @return Uma lista de DTOs das categorias encontradas.
     */
    List<CategoryResponse> findCategoriesByUser(String userEmail);

    /**
     * Atualiza uma categoria existente do utilizador autenticado.
     * @param categoryId ID da categoria a ser atualizada.
     * @param request DTO com os novos dados.
     * @param userEmail Email do utilizador autenticado.
     * @return DTO com os dados da categoria atualizada.
     */
    CategoryResponse updateCategory(Long categoryId, CategoryRequest request, String userEmail);

    /**
     * Apaga uma categoria do utilizador autenticado.
     * @param categoryId ID da categoria a ser apagada.
     * @param userEmail Email do utilizador autenticado.
     */
    void deleteCategory(Long categoryId, String userEmail);
}
