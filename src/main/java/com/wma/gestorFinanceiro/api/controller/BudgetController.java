package com.wma.gestorFinanceiro.api.controller;

import com.wma.gestorFinanceiro.api.dto.budget.BudgetRequest;
import com.wma.gestorFinanceiro.api.dto.budget.BudgetResponse;
import com.wma.gestorFinanceiro.service.BudgetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/budgets")
@RequiredArgsConstructor
public class BudgetController {

    private final BudgetService budgetService;

    @PostMapping
    public ResponseEntity<BudgetResponse> create(@RequestBody @Valid BudgetRequest request, Principal principal) {
        BudgetResponse response = budgetService.createBudget(request, principal.getName());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<BudgetResponse>> list(Principal principal) {
        List<BudgetResponse> budgets = budgetService.findBudgetsByUser(principal.getName());
        return ResponseEntity.ok(budgets);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BudgetResponse> update(@PathVariable Long id, @RequestBody @Valid BudgetRequest request, Principal principal) {
        BudgetResponse response = budgetService.updateBudget(id, request, principal.getName());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, Principal principal) {
        budgetService.deleteBudget(id, principal.getName());
        return ResponseEntity.noContent().build();
    }
}
