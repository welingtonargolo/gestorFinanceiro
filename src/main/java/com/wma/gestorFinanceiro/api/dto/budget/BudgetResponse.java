package com.wma.gestorFinanceiro.api.dto.budget;

import java.math.BigDecimal;
import java.time.LocalDate;

public record BudgetResponse(
    Long id,
    BigDecimal amount,
    LocalDate monthYear,
    Long categoryId,
    String categoryName
) {
}
