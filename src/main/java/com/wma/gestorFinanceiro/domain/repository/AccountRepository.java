package com.wma.gestorFinanceiro.domain.repository;

import com.wma.gestorFinanceiro.domain.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {

    /**
     * Encontra todas as contas associadas a um ID de utilizador específico.
     * @param userId O ID do utilizador.
     * @return Uma lista de contas do utilizador.
     */
    List<Account> findByUserId(Long userId);
}
