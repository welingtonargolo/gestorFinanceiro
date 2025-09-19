package com.wma.gestorFinanceiro.service.impl;

import com.wma.gestorFinanceiro.api.dto.user.UserRequest;
import com.wma.gestorFinanceiro.api.dto.user.UserResponse;
import com.wma.gestorFinanceiro.domain.entity.User;
import com.wma.gestorFinanceiro.domain.repository.UserRepository;
import com.wma.gestorFinanceiro.exception.BusinessException;
import com.wma.gestorFinanceiro.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserResponse createUser(UserRequest userRequest) {
        // 1. Validação da regra de negócio: Verificar se o e-mail já existe
        userRepository.findByEmail(userRequest.email())
                .ifPresent(user -> {
                    throw new BusinessException("O e-mail informado já está em uso.");
                });

        // 2. Mapeamento do DTO para a Entidade
        User newUser = User.builder()
                .name(userRequest.name())
                .email(userRequest.email())
                .password(passwordEncoder.encode(userRequest.password())) // 3. Criptografia da senha
                .build();

        // 4. Persistência no banco de dados
        User savedUser = userRepository.save(newUser);

        // 5. Mapeamento da Entidade para o DTO de Resposta e retorno
        return new UserResponse(savedUser);
    }
}
