package com.wma.gestorFinanceiro.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // Desabilita CSRF, comum em APIs stateless
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Define a política de sessão como stateless
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/api/users/register").permitAll() // Permite acesso público ao endpoint de registro
                        // .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll() // (Opcional) Libera o Swagger
                        .anyRequest().authenticated() // Exige autenticação para todas as outras requisições
                )
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Utiliza o BCrypt para a codificação de senhas, que é o padrão de mercado.
        return new BCryptPasswordEncoder();
    }
}
