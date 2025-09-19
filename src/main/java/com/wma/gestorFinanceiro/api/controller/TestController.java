package com.wma.gestorFinanceiro.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    /**
     * Endpoint protegido para verificar a autenticação JWT.
     * Só pode ser acedido com um token válido.
     * @return Uma mensagem de sucesso com o nome do utilizador autenticado.
     */
    @GetMapping("/protected")
    public ResponseEntity<String> getProtectedResource() {
        // Obtém os detalhes do utilizador autenticado a partir do contexto de segurança
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        String message = String.format("Olá, %s! Você tem acesso a este recurso protegido.", currentUserName);

        return ResponseEntity.ok(message);
    }
}
