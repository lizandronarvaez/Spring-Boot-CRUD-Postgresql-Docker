package com.dashboard.dashboardinventario.security.auth.services;


import com.dashboard.dashboardinventario.security.auth.controllers.AuthResponse;
import com.dashboard.dashboardinventario.security.auth.models.dto.UserDto;

public interface AuthService {
    // Metodo para registrar un usuario
    AuthResponse registerUser(UserDto userDto);

    // Metodo para logear un usuario
    AuthResponse loginUser(UserDto userDto);

}
