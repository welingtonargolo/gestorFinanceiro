package com.wma.gestorFinanceiro.api.controller;

import com.wma.gestorFinanceiro.api.dto.transaction.TransactionRequest;
import com.wma.gestorFinanceiro.api.dto.transaction.TransactionResponse;
import com.wma.gestorFinanceiro.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransactionResponse> createTransaction(@RequestBody @Valid TransactionRequest request, Principal principal) {
        TransactionResponse newTransaction = transactionService.createTransaction(request, principal.getName());
        return new ResponseEntity<>(newTransaction, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TransactionResponse>> getUserTransactions(Principal principal) {
        List<TransactionResponse> transactions = transactionService.findTransactionsByUser(principal.getName());
        return ResponseEntity.ok(transactions);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionResponse> updateTransaction(@PathVariable("id") Long transactionId,
                                                                 @RequestBody @Valid TransactionRequest request,
                                                                 Principal principal) {
        TransactionResponse updatedTransaction = transactionService.updateTransaction(transactionId, request, principal.getName());
        return ResponseEntity.ok(updatedTransaction);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable("id") Long transactionId, Principal principal) {
        transactionService.deleteTransaction(transactionId, principal.getName());
        return ResponseEntity.noContent().build();
    }
}
