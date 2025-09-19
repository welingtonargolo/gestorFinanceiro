package com.wma.gestorFinanceiro.api.controller;

import com.wma.gestorFinanceiro.api.dto.user.UserRequest;
import com.wma.gestorFinanceiro.api.dto.user.UserResponse;
import com.wma.gestorFinanceiro.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Endpoint para registrar um novo usuário no sistema.
     * @param userRequest Os dados do usuário a serem registrados.
     * @return Os dados do usuário criado com o status HTTP 201 (Created).
     */
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody @Valid UserRequest userRequest) {
        UserResponse createdUser = userService.createUser(userRequest);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }
}
