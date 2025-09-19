package com.wma.gestorFinanceiro.service.impl;

import com.wma.gestorFinanceiro.api.dto.dashboard.BudgetSummaryDTO;
import com.wma.gestorFinanceiro.api.dto.dashboard.DashboardResponse;
import com.wma.gestorFinanceiro.domain.entity.Budget;
import com.wma.gestorFinanceiro.domain.entity.User;
import com.wma.gestorFinanceiro.domain.enums.TransactionType;
import com.wma.gestorFinanceiro.domain.repository.AccountRepository;
import com.wma.gestorFinanceiro.domain.repository.BudgetRepository;
import com.wma.gestorFinanceiro.domain.repository.TransactionRepository;
import com.wma.gestorFinanceiro.domain.repository.UserRepository;
import com.wma.gestorFinanceiro.exception.ResourceNotFoundException;
import com.wma.gestorFinanceiro.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final BudgetRepository budgetRepository;

    @Override
    @Transactional(readOnly = true)
    public DashboardResponse getDashboardData(String userEmail) {
        User user = findUserByEmail(userEmail);
        Long userId = user.getId();

        LocalDate today = LocalDate.now();
        LocalDate startDate = today.withDayOfMonth(1);
        LocalDate endDate = today.withDayOfMonth(today.lengthOfMonth());

        BigDecimal totalBalance = calculateTotalBalance(userId);
        // --- CORREÇÃO APLICADA ---
        BigDecimal totalIncomeMonth = transactionRepository.sumAmountByUserIdAndTypeAndDateBetween(userId, TransactionType.RECEITA, startDate, endDate);
        BigDecimal totalExpensesMonth = transactionRepository.sumAmountByUserIdAndTypeAndDateBetween(userId, TransactionType.DESPESA, startDate, endDate);
        List<BudgetSummaryDTO> budgetSummaries = getBudgetSummaries(userId, startDate, endDate);

        return new DashboardResponse(totalBalance, totalIncomeMonth, totalExpensesMonth, budgetSummaries);
    }

    private BigDecimal calculateTotalBalance(Long userId) {
        BigDecimal initialBalances = accountRepository.sumInitialBalanceByUserId(userId);
        // --- CORREÇÃO APLICADA ---
        BigDecimal totalIncomes = transactionRepository.sumAmountByUserIdAndType(userId, TransactionType.RECEITA);
        BigDecimal totalExpenses = transactionRepository.sumAmountByUserIdAndType(userId, TransactionType.DESPESA);
        return initialBalances.add(totalIncomes).subtract(totalExpenses);
    }

    private List<BudgetSummaryDTO> getBudgetSummaries(Long userId, LocalDate startDate, LocalDate endDate) {
        List<Budget> budgetsForMonth = budgetRepository.findByUserIdAndMonthYear(userId, startDate);

        return budgetsForMonth.stream().map(budget -> {
            BigDecimal spentAmount = transactionRepository.sumExpensesByCategoryIdAndDateBetween(
                userId, budget.getCategory().getId(), startDate, endDate);

            BigDecimal budgetedAmount = budget.getAmount();
            BigDecimal remainingAmount = budgetedAmount.subtract(spentAmount);
            BigDecimal percentageSpent = BigDecimal.ZERO;

            if (budgetedAmount.compareTo(BigDecimal.ZERO) > 0) {
                percentageSpent = spentAmount.divide(budgetedAmount, 4, RoundingMode.HALF_UP)
                    .multiply(new BigDecimal("100"));
            }

            return new BudgetSummaryDTO(
                budget.getCategory().getName(),
                budgetedAmount,
                spentAmount,
                remainingAmount,
                percentageSpent
            );
        }).collect(Collectors.toList());
    }

    private User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("Utilizador não encontrado com o e-mail: " + email));
    }
}

