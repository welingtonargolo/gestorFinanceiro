package com.wma.gestorFinanceiro.security;

import com.wma.gestorFinanceiro.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Carrega os dados de um utilizador pelo seu username (que no nosso caso é o e-mail).
     * Este método é usado pelo Spring Security durante o processo de autenticação.
     * @param email O e-mail do utilizador a ser carregado.
     * @return um objeto UserDetails contendo os dados do utilizador.
     * @throws UsernameNotFoundException se o utilizador não for encontrado.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilizador não encontrado com o e-mail: " + email));
    }
}
