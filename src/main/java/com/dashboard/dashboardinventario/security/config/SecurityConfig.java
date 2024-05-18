package com.dashboard.dashboardinventario.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.dashboard.dashboardinventario.security.jwt.filters.JwtAuthenticationFilter;

@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                // Solo para vistas propias de java
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((auth) -> {
                    // Rutas que puedes acceder sin que inicies sesion
                    // !!TODO: CORREGIR UNA VEZ REALIZADO TODA LA LOGICA - DESARROLLO EN PRUEBAS
                    auth
                            // Productos
                            .requestMatchers(HttpMethod.GET, "/products").permitAll()
                            // Categorias
                            .requestMatchers(HttpMethod.GET, "/categories").permitAll()
                            // Ruta de autenticaciones
                            .requestMatchers("/auth/**").permitAll()
                            // ruta de clientes
                            .requestMatchers(HttpMethod.GET, "/clients/login").permitAll()
                            .requestMatchers(HttpMethod.POST, "/clients").permitAll()
                            // Rutas que solo puedes acceder si estas logeado
                            .anyRequest().authenticated();
                })
                .addFilterAfter(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(authenticationProvider)
                .build();
    }

    // Autentication manager(toda solicitud pasa por este filtro)
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
