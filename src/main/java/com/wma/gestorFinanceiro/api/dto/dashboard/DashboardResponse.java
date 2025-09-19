package com.wma.gestorFinanceiro.api.dto.dashboard;

import java.math.BigDecimal;
import java.util.List;

public record DashboardResponse(
    BigDecimal totalBalance,
    BigDecimal totalIncomeMonth,
    BigDecimal totalExpensesMonth,
    List<BudgetSummaryDTO> budgetSummaries
) {
}
