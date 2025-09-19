package com.wma.gestorFinanceiro.api.dto.account;

import com.wma.gestorFinanceiro.domain.enums.AccountType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record AccountRequest(
    @NotBlank(message = "O nome da conta é obrigatório")
    @Size(min = 3, max = 100, message = "O nome da conta deve ter entre 3 e 100 caracteres")
    String name,

    @NotNull(message = "O tipo da conta é obrigatório")
    AccountType type,

    @NotNull(message = "O saldo inicial é obrigatório")
    @DecimalMin(value = "0.0", message = "O saldo inicial não pode ser negativo")
    BigDecimal initialBalance
) {
}
