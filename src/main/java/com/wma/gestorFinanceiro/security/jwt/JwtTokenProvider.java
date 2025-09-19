package com.wma.gestorFinanceiro.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration-ms}")
    private int jwtExpirationInMs;

    /**
     * Gera um token JWT para um usuário autenticado.
     * @param authentication O objeto de autenticação do Spring Security.
     * @return Uma String contendo o token JWT.
     */
    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .subject(username)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getSignInKey())
                .compact();
    }

    /**
     * Extrai o nome de usuário (e-mail) de um token JWT.
     * @param token O token JWT.
     * @return O e-mail do usuário contido no token.
     */
    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getSubject();
    }

    /**
     * Valida se um token JWT está correto, bem formado e não expirou.
     * @param authToken O token a ser validado.
     * @return true se o token for válido, false caso contrário.
     */
    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().verifyWith(getSignInKey()).build().parseSignedClaims(authToken);
            return true;
        } catch (MalformedJwtException ex) {
            logger.error("Token JWT inválido");
        } catch (ExpiredJwtException ex) {
            logger.error("Token JWT expirado");
        } catch (UnsupportedJwtException ex) {
            logger.error("Token JWT não suportado");
        } catch (IllegalArgumentException ex) {
            logger.error("A string de claims do JWT está vazia.");
        }
        return false;
    }

    /**
     * Gera a chave secreta para assinar e validar o token a partir da string no application.properties.
     * @return A chave secreta do tipo SecretKey.
     */
    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
