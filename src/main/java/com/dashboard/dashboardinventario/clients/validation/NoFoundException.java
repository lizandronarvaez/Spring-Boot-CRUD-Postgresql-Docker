package com.dashboard.dashboardinventario.clients.validation;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class NoFoundException {

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<?> handleResourceNoFoundException(NoResourceFoundException ex) {
        AuthErrorResponse responseBody = AuthErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message("¡No existen clientes disponibles!")
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(BindingResult result) {
        Map<String, Object> error = new HashMap<>();
        if (result.hasErrors()) {
            result.getFieldErrors().forEach(field -> {
                error.put(field.getField(), field.getDefaultMessage());
            });
        }
        AuthErrorResponse response = AuthErrorResponse
                .builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(error)
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
