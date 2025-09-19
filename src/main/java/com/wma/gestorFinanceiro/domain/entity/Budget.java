package com.wma.gestorFinanceiro.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "budgets", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"user_id", "category_id", "monthYear"})
})
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O valor do orçamento é obrigatório")
    @Column(nullable = false)
    private BigDecimal amount;

    @NotNull(message = "O mês/ano do orçamento é obrigatório")
    @Column(nullable = false)
    private LocalDate monthYear; // Armazenará o primeiro dia do mês do orçamento

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
