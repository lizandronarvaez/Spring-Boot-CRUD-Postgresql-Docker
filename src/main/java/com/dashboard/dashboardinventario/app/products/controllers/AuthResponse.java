package com.dashboard.dashboardinventario.app.products.controllers;

import com.dashboard.dashboardinventario.app.products.models.entity.ProductEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AuthResponse {
    
    private Integer status;
    private ProductEntity product;
    private String message;
}
