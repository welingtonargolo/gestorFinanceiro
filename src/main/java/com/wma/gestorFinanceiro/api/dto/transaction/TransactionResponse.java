package com.wma.gestorFinanceiro.api.dto.transaction;

import com.wma.gestorFinanceiro.domain.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionResponse(
    Long id,
    String description,
    BigDecimal amount,
    LocalDate date,
    TransactionType type,
    Long categoryId,
    String categoryName
) {
}
