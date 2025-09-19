package com.wma.gestorFinanceiro.service.impl;

import com.wma.gestorFinanceiro.api.dto.account.AccountRequest;
import com.wma.gestorFinanceiro.api.dto.account.AccountResponse;
import com.wma.gestorFinanceiro.domain.entity.Account;
import com.wma.gestorFinanceiro.domain.entity.User;
import com.wma.gestorFinanceiro.domain.repository.AccountRepository;
import com.wma.gestorFinanceiro.domain.repository.UserRepository;
import com.wma.gestorFinanceiro.exception.ResourceNotFoundException;
import com.wma.gestorFinanceiro.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public AccountResponse createAccount(AccountRequest request, String userEmail) {
        User user = findUserByEmail(userEmail);

        Account account = Account.builder()
                .name(request.name())
                .type(request.type())
                .initialBalance(request.initialBalance())
                .user(user)
                .build();

        Account savedAccount = accountRepository.save(account);
        return toAccountResponse(savedAccount);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountResponse> findAccountsByUser(String userEmail) {
        User user = findUserByEmail(userEmail);
        return accountRepository.findByUserId(user.getId())
                .stream()
                .map(this::toAccountResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AccountResponse updateAccount(Long accountId, AccountRequest request, String userEmail) {
        User user = findUserByEmail(userEmail);
        Account account = findAccountByIdAndUser(accountId, user.getId());

        account.setName(request.name());
        account.setType(request.type());
        account.setInitialBalance(request.initialBalance());

        Account updatedAccount = accountRepository.save(account);
        return toAccountResponse(updatedAccount);
    }

    @Override
    @Transactional
    public void deleteAccount(Long accountId, String userEmail) {
        User user = findUserByEmail(userEmail);
        Account account = findAccountByIdAndUser(accountId, user.getId());
        // Adicionar validação para não permitir apagar contas com transações associadas
        accountRepository.delete(account);
    }

    private User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilizador não encontrado com o e-mail: " + email));
    }

    private Account findAccountByIdAndUser(Long accountId, Long userId) {
        return accountRepository.findById(accountId)
                .filter(account -> account.getUser().getId().equals(userId))
                .orElseThrow(() -> new ResourceNotFoundException("Conta não encontrada ou não pertence ao utilizador."));
    }

    private AccountResponse toAccountResponse(Account account) {
        return new AccountResponse(
                account.getId(),
                account.getName(),
                account.getType(),
                account.getInitialBalance()
        );
    }
}
