package com.wma.gestorFinanceiro.api.controller;

import com.wma.gestorFinanceiro.api.dto.auth.LoginRequest;
import com.wma.gestorFinanceiro.api.dto.auth.LoginResponse;
import com.wma.gestorFinanceiro.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * Endpoint para autenticar um utilizador.
     * @param loginRequest DTO contendo e-mail e senha.
     * @return ResponseEntity com o DTO de resposta contendo o token JWT.
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse response = authService.login(loginRequest);
        return ResponseEntity.ok(response);
    }
}
