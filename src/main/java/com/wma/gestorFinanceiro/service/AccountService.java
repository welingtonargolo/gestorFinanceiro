package com.wma.gestorFinanceiro.service;

import com.wma.gestorFinanceiro.api.dto.account.AccountRequest;
import com.wma.gestorFinanceiro.api.dto.account.AccountResponse;

import java.util.List;

public interface AccountService {

    AccountResponse createAccount(AccountRequest request, String userEmail);

    List<AccountResponse> findAccountsByUser(String userEmail);

    AccountResponse updateAccount(Long accountId, AccountRequest request, String userEmail);

    void deleteAccount(Long accountId, String userEmail);
}
