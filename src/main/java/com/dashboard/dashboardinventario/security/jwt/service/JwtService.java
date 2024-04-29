package com.dashboard.dashboardinventario.security.jwt.service;

import org.springframework.security.core.userdetails.UserDetails;
import io.jsonwebtoken.Claims;

public interface JwtService {
    public String generateToken(UserDetails userDetails);

    // Extrae el subjec del token
    public String extractUsername(String token);

    // Valida el token
    public Boolean validateToken(String token, UserDetails userDetails) ;

    // Obtiene el token del request y lo verify
    public Claims getTokenBody(String token);

    // Comprueba que el token no haya expirado
    public boolean isTokenExpired(String token);   
}
