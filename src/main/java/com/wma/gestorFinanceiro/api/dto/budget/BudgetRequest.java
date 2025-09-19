package com.wma.gestorFinanceiro.api.dto.budget;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record BudgetRequest(
    @NotNull(message = "O valor do orçamento é obrigatório")
    @DecimalMin(value = "0.01", message = "O valor do orçamento deve ser maior que zero")
    BigDecimal amount,

    @NotNull(message = "O mês/ano do orçamento é obrigatório")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDate monthYear,

    @NotNull(message = "O ID da categoria é obrigatório")
    Long categoryId
) {
}
