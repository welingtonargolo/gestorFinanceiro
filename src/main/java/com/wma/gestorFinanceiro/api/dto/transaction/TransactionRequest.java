package com.wma.gestorFinanceiro.api.dto.transaction;

import com.wma.gestorFinanceiro.domain.enums.TransactionType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionRequest(
    @NotBlank(message = "A descrição é obrigatória")
    @Size(min = 3, max = 100, message = "A descrição deve ter entre 3 e 100 caracteres")
    String description,

    @NotNull(message = "O valor é obrigatório")
    @DecimalMin(value = "0.01", message = "O valor deve ser maior que zero")
    BigDecimal amount,

    @NotNull(message = "A data é obrigatória")
    LocalDate date,

    @NotNull(message = "O tipo da transação é obrigatório (RECEITA ou DESPESA)")
    TransactionType type,

    @NotNull(message = "O ID da categoria é obrigatório")
    Long categoryId
) {
}
