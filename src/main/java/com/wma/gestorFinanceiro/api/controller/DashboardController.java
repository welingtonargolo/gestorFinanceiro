package com.wma.gestorFinanceiro.api.controller;

import com.wma.gestorFinanceiro.api.dto.dashboard.DashboardResponse;
import com.wma.gestorFinanceiro.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping
    public ResponseEntity<DashboardResponse> getDashboard(Principal principal) {
        DashboardResponse dashboardData = dashboardService.getDashboardData(principal.getName());
        return ResponseEntity.ok(dashboardData);
    }
}
