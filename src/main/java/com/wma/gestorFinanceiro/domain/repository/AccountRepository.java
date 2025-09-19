package com.wma.gestorFinanceiro.domain.repository;

import com.wma.gestorFinanceiro.domain.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByIdAndUserId(Long id, Long userId);

    List<Account> findByUserId(Long userId);

    @Query("SELECT COALESCE(SUM(a.initialBalance), 0) FROM Account a WHERE a.user.id = :userId")
    BigDecimal sumInitialBalanceByUserId(Long userId);
}

