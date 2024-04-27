package com.dashboard.dashboardinventario.app.clients.models.dto;

import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ClientDto {

    @NotBlank(message = "El nombre es obligatorio")
    private String fullname;

    @Email(message = "Por favor,ingrese un email válido")
    @NotBlank(message = "El email es obligatorio")
    private String email;

    @NotBlank(message = "El password es obligatorio")
    private String password;

    @NotBlank(message = "Por favor, proporcione un número de telefono válido")
    private String phone;

    @NotBlank(message = "Por favor, proporcione su dirección completa")
    private String address;

    @NotBlank(message = "El código postal es obligatorio")
    private String postalcode;

    @NotBlank(message = "Por favor, especifique la ciudad")
    private String city;

    @NotBlank(message = "Por favor, especifique el país")
    private String country;

    @PrePersist
    public void inputClean() {
        this.fullname = this.fullname.trim().replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        this.email = this.email.trim().replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        this.address = this.address.trim().toLowerCase();
        this.postalcode = this.postalcode.trim().replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        this.city = this.city.trim().replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        this.country = this.country.trim().replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
    }
}
