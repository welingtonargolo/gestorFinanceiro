package com.wma.gestorFinanceiro.api.dto.dashboard;

import java.math.BigDecimal;

public record BudgetSummaryDTO(
    String categoryName,
    BigDecimal budgetedAmount,
    BigDecimal spentAmount,
    BigDecimal remainingAmount,
    BigDecimal percentageSpent
) {
}
