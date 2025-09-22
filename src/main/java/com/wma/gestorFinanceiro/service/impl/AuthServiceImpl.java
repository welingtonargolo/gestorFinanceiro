package com.wma.gestorFinanceiro.service.impl;

import com.wma.gestorFinanceiro.api.dto.auth.LoginRequest;
import com.wma.gestorFinanceiro.api.dto.auth.LoginResponse;
import com.wma.gestorFinanceiro.security.jwt.JwtTokenProvider;
import com.wma.gestorFinanceiro.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import com.wma.gestorFinanceiro.security.UserDetailsServiceImpl;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password())
        );

        String accessToken = tokenProvider.generateAccessToken(authentication);
        String refreshToken = tokenProvider.generateRefreshToken(authentication);

        return new LoginResponse(accessToken, refreshToken);
    }

    @Override
    public LoginResponse refreshToken(String refreshToken) {
        if (tokenProvider.validateToken(refreshToken)) {
            String username = tokenProvider.getUsernameFromJWT(refreshToken);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            String newAccessToken = tokenProvider.generateAccessToken(authentication);
            String newRefreshToken = tokenProvider.generateRefreshToken(authentication);

            return new LoginResponse(newAccessToken, newRefreshToken);
        } else {
            throw new RuntimeException("Refresh token inválido ou expirado");
        }
    }
}
