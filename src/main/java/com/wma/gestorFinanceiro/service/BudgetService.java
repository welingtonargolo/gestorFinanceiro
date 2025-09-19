package com.wma.gestorFinanceiro.service;

import com.wma.gestorFinanceiro.api.dto.budget.BudgetRequest;
import com.wma.gestorFinanceiro.api.dto.budget.BudgetResponse;

import java.util.List;

public interface BudgetService {
    BudgetResponse createBudget(BudgetRequest request, String userEmail);
    List<BudgetResponse> findBudgetsByUser(String userEmail);
    BudgetResponse updateBudget(Long budgetId, BudgetRequest request, String userEmail);
    void deleteBudget(Long budgetId, String userEmail);
}
