package com.wma.gestorFinanceiro.api.dto.user;

import com.wma.gestorFinanceiro.domain.entity.User;

import java.time.LocalDateTime;

public record UserResponse(
        Long id,
        String name,
        String email,
        LocalDateTime createdAt
) {
    // Construtor de conveniência para mapear da entidade User para o DTO
    public UserResponse(User user) {
        this(user.getId(), user.getName(), user.getEmail(), user.getCreatedAt());
    }
}
