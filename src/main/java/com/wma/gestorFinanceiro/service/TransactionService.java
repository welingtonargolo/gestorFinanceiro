package com.wma.gestorFinanceiro.service;

import com.wma.gestorFinanceiro.api.dto.transaction.TransactionRequest;
import com.wma.gestorFinanceiro.api.dto.transaction.TransactionResponse;

import java.util.List;

/**
 * Interface para o serviço de gestão de transações financeiras.
 */
public interface TransactionService {

    /**
     * Cria uma nova transação para o utilizador autenticado.
     * @param request DTO com os dados da nova transação.
     * @param userEmail Email do utilizador autenticado.
     * @return DTO com os dados da transação criada.
     */
    TransactionResponse createTransaction(TransactionRequest request, String userEmail);

    /**
     * Lista todas as transações do utilizador autenticado.
     * @param userEmail Email do utilizador autenticado.
     * @return Uma lista de DTOs das transações encontradas.
     */
    List<TransactionResponse> findTransactionsByUser(String userEmail);

    /**
     * Atualiza uma transação existente do utilizador autenticado.
     * @param transactionId ID da transação a ser atualizada.
     * @param request DTO com os novos dados.
     * @param userEmail Email do utilizador autenticado.
     * @return DTO com os dados da transação atualizada.
     */
    TransactionResponse updateTransaction(Long transactionId, TransactionRequest request, String userEmail);

    /**
     * Apaga uma transação do utilizador autenticado.
     * @param transactionId ID da transação a ser apagada.
     * @param userEmail Email do utilizador autenticado.
     */
    void deleteTransaction(Long transactionId, String userEmail);
}
