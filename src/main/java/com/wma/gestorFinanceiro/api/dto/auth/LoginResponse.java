package com.wma.gestorFinanceiro.api.dto.auth;

public record LoginResponse(
        String token,
        String refreshToken

) {
}
