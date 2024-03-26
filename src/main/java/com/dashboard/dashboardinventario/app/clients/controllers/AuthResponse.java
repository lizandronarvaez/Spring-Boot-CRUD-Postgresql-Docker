package com.dashboard.dashboardinventario.app.clients.controllers;

import com.dashboard.dashboardinventario.app.clients.models.entities.ClientEntity;

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
    private ClientEntity client;
    private String message;
    private String token;
}
