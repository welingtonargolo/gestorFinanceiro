package com.wma.gestorFinanceiro.service;

import com.wma.gestorFinanceiro.api.dto.auth.LoginRequest;
import com.wma.gestorFinanceiro.api.dto.auth.LoginResponse;

public interface AuthService {
    /**
     * Autentica um utilizador e retorna um token JWT.
     * @param loginRequest O DTO com as credenciais de login (e-mail e senha).
     * @return O DTO de resposta contendo o token de acesso.
     */
    LoginResponse login(LoginRequest loginRequest);
}
