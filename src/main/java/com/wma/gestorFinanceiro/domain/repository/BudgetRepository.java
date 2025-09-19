package com.wma.gestorFinanceiro.domain.repository;

import com.wma.gestorFinanceiro.domain.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BudgetRepository extends JpaRepository<Budget, Long> {

    List<Budget> findByUserId(Long userId);

    List<Budget> findByUserIdAndMonthYear(Long userId, LocalDate monthYear);

    Optional<Budget> findByUserIdAndCategoryIdAndMonthYear(Long userId, Long categoryId, LocalDate monthYear);
}
