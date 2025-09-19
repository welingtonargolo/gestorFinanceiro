package com.wma.gestorFinanceiro.service;

import com.wma.gestorFinanceiro.api.dto.dashboard.DashboardResponse;

public interface DashboardService {
    DashboardResponse getDashboardData(String userEmail);
}
