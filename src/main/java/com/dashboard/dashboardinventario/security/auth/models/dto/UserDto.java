package com.dashboard.dashboardinventario.security.auth.models.dto;

import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserDto {

    @NotBlank(message = "El nombre es obligatorio")
    private String fullname;

    @Email(message = "Por favor,ingrese un email válido")
    @NotBlank(message = "El email es obligatorio")
    private String email;

    @NotBlank(message = "El password es obligatorio")
    private String password;

    // Limpieza de datos
    @PrePersist
    public void inputClean() {
        this.fullname = this.fullname.trim().replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        this.email = this.email.trim().replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
    }
}
