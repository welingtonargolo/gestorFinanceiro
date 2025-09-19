package com.wma.gestorFinanceiro.domain.repository;

import com.wma.gestorFinanceiro.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Busca um usuário pelo seu endereço de e-mail.
     * @param email O e-mail do usuário a ser buscado.
     * @return um Optional contendo o usuário se encontrado, ou vazio caso contrário.
     */
    Optional<User> findByEmail(String email);

}
