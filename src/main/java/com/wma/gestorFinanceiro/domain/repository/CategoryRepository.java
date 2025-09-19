package com.wma.gestorFinanceiro.domain.repository;

import com.wma.gestorFinanceiro.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * Encontra todas as categorias pertencentes a um utilizador específico.
     * @param userId O ID do utilizador.
     * @return Uma lista de categorias.
     */
    List<Category> findByUserId(Long userId);

}
