package com.wma.gestorFinanceiro.service.impl;

import com.wma.gestorFinanceiro.api.dto.budget.BudgetRequest;
import com.wma.gestorFinanceiro.api.dto.budget.BudgetResponse;
import com.wma.gestorFinanceiro.domain.entity.Budget;
import com.wma.gestorFinanceiro.domain.entity.Category;
import com.wma.gestorFinanceiro.domain.entity.User;
import com.wma.gestorFinanceiro.domain.repository.BudgetRepository;
import com.wma.gestorFinanceiro.domain.repository.CategoryRepository;
import com.wma.gestorFinanceiro.domain.repository.UserRepository;
import com.wma.gestorFinanceiro.exception.BusinessException;
import com.wma.gestorFinanceiro.exception.ResourceNotFoundException;
import com.wma.gestorFinanceiro.service.BudgetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BudgetServiceImpl implements BudgetService {

    private final BudgetRepository budgetRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public BudgetResponse createBudget(BudgetRequest request, String userEmail) {
        User user = findUserByEmail(userEmail);
        Category category = findCategoryByIdAndUser(request.categoryId(), user.getId());

        LocalDate firstDayOfMonth = request.monthYear().withDayOfMonth(1);

        budgetRepository.findByUserIdAndCategoryIdAndMonthYear(user.getId(), category.getId(), firstDayOfMonth)
            .ifPresent(b -> {
                throw new BusinessException("Já existe um orçamento para esta categoria neste mês.");
            });

        Budget newBudget = Budget.builder()
            .user(user)
            .category(category)
            .amount(request.amount())
            .monthYear(firstDayOfMonth)
            .build();

        Budget savedBudget = budgetRepository.save(newBudget);
        return toBudgetResponse(savedBudget);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BudgetResponse> findBudgetsByUser(String userEmail) {
        User user = findUserByEmail(userEmail);
        return budgetRepository.findByUserId(user.getId()).stream()
            .map(this::toBudgetResponse)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public BudgetResponse updateBudget(Long budgetId, BudgetRequest request, String userEmail) {
        User user = findUserByEmail(userEmail);
        Budget budget = findBudgetByIdAndUser(budgetId, user.getId());

        // Por enquanto, permitimos apenas a atualização do valor.
        // Mudar categoria ou data seria mais complexo e poderia exigir apagar e criar um novo.
        budget.setAmount(request.amount());

        Budget updatedBudget = budgetRepository.save(budget);
        return toBudgetResponse(updatedBudget);
    }

    @Override
    @Transactional
    public void deleteBudget(Long budgetId, String userEmail) {
        User user = findUserByEmail(userEmail);
        Budget budget = findBudgetByIdAndUser(budgetId, user.getId());
        budgetRepository.delete(budget);
    }

    private User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("Utilizador não encontrado com o e-mail: " + email));
    }

    private Category findCategoryByIdAndUser(Long categoryId, Long userId) {
        return categoryRepository.findByIdAndUserId(categoryId, userId)
            .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada ou não pertence ao utilizador"));
    }
    
    private Budget findBudgetByIdAndUser(Long budgetId, Long userId) {
        return budgetRepository.findById(budgetId)
            .filter(budget -> budget.getUser().getId().equals(userId))
            .orElseThrow(() -> new ResourceNotFoundException("Orçamento não encontrado ou não pertence ao utilizador"));
    }

    private BudgetResponse toBudgetResponse(Budget budget) {
        return new BudgetResponse(
            budget.getId(),
            budget.getAmount(),
            budget.getMonthYear(),
            budget.getCategory().getId(),
            budget.getCategory().getName()
        );
    }
}
