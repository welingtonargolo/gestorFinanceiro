package com.wma.gestorFinanceiro.api.controller;

import com.wma.gestorFinanceiro.api.dto.account.AccountRequest;
import com.wma.gestorFinanceiro.api.dto.account.AccountResponse;
import com.wma.gestorFinanceiro.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@RequestBody @Valid AccountRequest request, Principal principal) {
        AccountResponse newAccount = accountService.createAccount(request, principal.getName());
        return new ResponseEntity<>(newAccount, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AccountResponse>> getUserAccounts(Principal principal) {
        List<AccountResponse> accounts = accountService.findAccountsByUser(principal.getName());
        return ResponseEntity.ok(accounts);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountResponse> updateAccount(@PathVariable("id") Long accountId,
                                                         @RequestBody @Valid AccountRequest request,
                                                         Principal principal) {
        AccountResponse updatedAccount = accountService.updateAccount(accountId, request, principal.getName());
        return ResponseEntity.ok(updatedAccount);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable("id") Long accountId, Principal principal) {
        accountService.deleteAccount(accountId, principal.getName());
        return ResponseEntity.noContent().build();
    }
}
