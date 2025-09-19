package com.wma.gestorFinanceiro.service.impl;

import com.wma.gestorFinanceiro.api.dto.auth.LoginRequest;
import com.wma.gestorFinanceiro.api.dto.auth.LoginResponse;
import com.wma.gestorFinanceiro.security.jwt.JwtTokenProvider;
import com.wma.gestorFinanceiro.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        // 1. Cria um objeto de autenticação com as credenciais fornecidas
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.email(),
                        loginRequest.password()
                )
        );

        // 2. Se a autenticação for bem-sucedida, define-a no contexto de segurança
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 3. Gera o token JWT
        String jwt = tokenProvider.generateToken(authentication);

        // 4. Retorna o token na resposta
        return new LoginResponse(jwt);
    }
}
