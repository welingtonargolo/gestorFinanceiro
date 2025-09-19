package com.wma.gestorFinanceiro.api.dto.account;

import com.wma.gestorFinanceiro.domain.enums.AccountType;

import java.math.BigDecimal;

public record AccountResponse(
    Long id,
    String name,
    AccountType type,
    BigDecimal initialBalance
) {
}
