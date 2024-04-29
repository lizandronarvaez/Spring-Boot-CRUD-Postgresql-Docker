package com.dashboard.dashboardinventario.app.products.models.dto;

import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProductDto {

    @NotBlank(message = "El nombre es obligatorio")
    private String fullname;

    private String description;

    @NotEmpty(message = "El precio es obligatorio")
    private double price;

    @NotEmpty(message = "Las cantidades son obligatorias")
    private Integer quantity;

    private String imageProduct;

    @NotBlank(message = "Introduce la categoria del producto")
    private String category;

    @PrePersist
    public void inputClean() {
        this.fullname = this.fullname.trim().replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        this.description = this.description.trim().replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        this.category = this.category.trim().replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
    }

}
