package com.wma.gestorFinanceiro.service.impl;

import com.wma.gestorFinanceiro.api.dto.transaction.TransactionRequest;
import com.wma.gestorFinanceiro.api.dto.transaction.TransactionResponse;
import com.wma.gestorFinanceiro.domain.entity.Category;
import com.wma.gestorFinanceiro.domain.entity.Transaction;
import com.wma.gestorFinanceiro.domain.entity.User;
import com.wma.gestorFinanceiro.domain.repository.CategoryRepository;
import com.wma.gestorFinanceiro.domain.repository.TransactionRepository;
import com.wma.gestorFinanceiro.domain.repository.UserRepository;
import com.wma.gestorFinanceiro.exception.BusinessException;
import com.wma.gestorFinanceiro.exception.ResourceNotFoundException;
import com.wma.gestorFinanceiro.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;


    @Override
    @Transactional
    public TransactionResponse createTransaction(TransactionRequest request, String userEmail) {
        User user = findUserByEmail(userEmail);
        Category category = findCategoryByIdAndUser(request.categoryId(), user.getId());

        Transaction transaction = Transaction.builder()
                .description(request.description())
                .amount(request.amount())
                .date(request.date())
                .type(request.type())
                .user(user)
                .category(category)
                .build();

        Transaction savedTransaction = transactionRepository.save(transaction);
        return toTransactionResponse(savedTransaction);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransactionResponse> findTransactionsByUser(String userEmail) {
        User user = findUserByEmail(userEmail);
        return transactionRepository.findByUserId(user.getId())
                .stream()
                .map(this::toTransactionResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TransactionResponse updateTransaction(Long transactionId, TransactionRequest request, String userEmail) {
        User user = findUserByEmail(userEmail);
        Transaction transaction = findTransactionByIdAndUser(transactionId, user.getId());
        Category category = findCategoryByIdAndUser(request.categoryId(), user.getId());

        transaction.setDescription(request.description());
        transaction.setAmount(request.amount());
        transaction.setDate(request.date());
        transaction.setType(request.type());
        transaction.setCategory(category);

        Transaction updatedTransaction = transactionRepository.save(transaction);
        return toTransactionResponse(updatedTransaction);
    }

    @Override
    @Transactional
    public void deleteTransaction(Long transactionId, String userEmail) {
        User user = findUserByEmail(userEmail);
        Transaction transaction = findTransactionByIdAndUser(transactionId, user.getId());
        transactionRepository.delete(transaction);
    }

    private User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilizador não encontrado com o e-mail: " + email));
    }

    private Category findCategoryByIdAndUser(Long categoryId, Long userId) {
        return categoryRepository.findById(categoryId)
                .filter(category -> category.getUser().getId().equals(userId))
                .orElseThrow(() -> new BusinessException("Categoria não encontrada ou não pertence ao utilizador."));
    }

    private Transaction findTransactionByIdAndUser(Long transactionId, Long userId) {
        return transactionRepository.findById(transactionId)
                .filter(transaction -> transaction.getUser().getId().equals(userId))
                .orElseThrow(() -> new ResourceNotFoundException("Transação não encontrada ou não pertence ao utilizador."));
    }

    private TransactionResponse toTransactionResponse(Transaction transaction) {
        return new TransactionResponse(
                transaction.getId(),
                transaction.getDescription(),
                transaction.getAmount(),
                transaction.getDate(),
                transaction.getType(),
                transaction.getCategory().getId(),
                transaction.getCategory().getName()
        );
    }
}
