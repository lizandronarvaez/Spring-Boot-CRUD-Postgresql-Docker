package com.dashboard.dashboardinventario.security.jwt.filters;

import com.dashboard.dashboardinventario.security.auth.models.entity.UserEntity;
import com.dashboard.dashboardinventario.security.auth.repository.UserRepository;
import com.dashboard.dashboardinventario.security.jwt.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        // Extraemos el token
        final String token = getTokenFromRequest(request);
        // Almacenar el usuario
        final String userEmailClaims;
        // Si el token recibido por la cabecera es nulo, no sigue la cadena de
        // filtro(es// como next en nodejs)
        if (token == null || !requiresAuthentication(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // Extrae el usuario del token
            userEmailClaims = jwtService.extractUsername(token);

            if (userEmailClaims != null || SecurityContextHolder.getContext().getAuthentication() == null) {
                // BUsca el usuario en la base de datos y comprueba que el usuario existe
                Optional<UserEntity> userOptional = userRepository.findByEmail(userEmailClaims);

                if (userOptional.isPresent()) {
                    // Crear un userDetails con los datos del usuario
                    UserDetails userDetails = userOptional.get();
                    // Verifica el usuario pasandole el token y el userDetails
                    if (jwtService.validateToken(token, userDetails)) {

                        // Iniciamos sesion y creamos el token de autenticacion
                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                                userEmailClaims, null, userDetails.getAuthorities());

                        // Se realiza la autenticacion con contextHolder
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                        // DETALLES PARA AÑADIR LA IP Y EL ID DE SESION
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    }
                }
            }
        } catch (Exception e) {
            sendUnauthorizedResponseWithMessage(response, e.getMessage());
            return;
        }
        // continua la cadena de filtros
        filterChain.doFilter(request, response);
    }

    // Obtener el token de la cabecera
    private String getTokenFromRequest(HttpServletRequest request) {
        // Extrae el token de la cabecera del header
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        // Comprueba el token si existe y lo extrae
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.split(" ")[1];
        }
        return null;
    }

    // Verificar si la solicitud requiere autenticación
    private boolean requiresAuthentication(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return !requestURI.equals("/api/auth/register")
                && !requestURI.equals("/api/auth/login")
                && !requestURI.equals("/api/app/publica")
                && !requestURI.equals("/api/clients/login")
                && !requestURI.equals("/api/clients/register")
                && !requestURI.equals("/api/products")
                && !requestURI.equals("/api/categories")
                && !requestURI.equals("/api/orders");
    }

    // Enviar mensaje de error en caso de error en el token
    private void sendUnauthorizedResponseWithMessage(HttpServletResponse response, String message) throws IOException {
        response.setContentType("UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(message);
    }

}
