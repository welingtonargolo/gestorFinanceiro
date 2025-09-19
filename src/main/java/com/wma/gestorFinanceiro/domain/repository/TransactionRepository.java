package com.wma.gestorFinanceiro.domain.repository;

import com.wma.gestorFinanceiro.domain.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    /**
     * Encontra todas as transações de um utilizador específico.
     * @param userId O ID do utilizador.
     * @return Uma lista de transações.
     */
    List<Transaction> findByUserId(Long userId);

}
