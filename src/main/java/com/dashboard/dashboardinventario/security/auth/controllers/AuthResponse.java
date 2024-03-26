package com.dashboard.dashboardinventario.security.auth.controllers;

import com.dashboard.dashboardinventario.security.auth.models.entity.UserEntity;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthResponse {

    private String message;
    private Integer status;
    private UserEntity userEntity;
    private String token;
}
