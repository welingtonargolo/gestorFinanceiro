package com.wma.gestorFinanceiro.service;

import com.wma.gestorFinanceiro.api.dto.user.UserRequest;
import com.wma.gestorFinanceiro.api.dto.user.UserResponse;

public interface UserService {

    /**
     * Cria um novo usuário no sistema.
     * @param userRequest DTO com os dados do usuário a ser criado.
     * @return DTO com os dados do usuário recém-criado.
     * @throws com.wma.gestorFinanceiro.exception.BusinessException se o e-mail já estiver em uso.
     */
    UserResponse createUser(UserRequest userRequest);

}
