package com.dashboard.dashboardinventario.security.auth.services;

import java.util.Optional;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dashboard.dashboardinventario.security.auth.controllers.AuthResponse;
import com.dashboard.dashboardinventario.security.auth.models.dto.UserDto;
import com.dashboard.dashboardinventario.security.auth.models.entity.UserEntity;
import com.dashboard.dashboardinventario.security.auth.repository.UserRepository;
import com.dashboard.dashboardinventario.security.auth.validation.EmailNotFoundException;
import com.dashboard.dashboardinventario.security.auth.validation.PasswordMismatchException;
import com.dashboard.dashboardinventario.security.jwt.service.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @SuppressWarnings("null")
    @Override
    public AuthResponse registerUser(UserDto userDto) {
        // Buscamos el usuario
        Optional<UserEntity> userExisting = userRepository.findByEmail(userDto.getEmail());
        // Comprobar que el usuario no exista
        if (userExisting.isPresent())
            throw new DuplicateKeyException(String.format("El email %s ya esta registrado", userDto.getEmail()));
        // Creamo un entity de usuario para guardarlo
        UserEntity userEntity = UserEntity
                .builder()
                .fullname(userDto.getFullname())
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .build();
        // Guardamos el usuario
        userRepository.save(userEntity);

        return AuthResponse
                .builder()
                .status(201)
                .message("¡Usuario creado correctamente!")
                .build();
    }

    @Override
    public AuthResponse loginUser(UserDto userDto) {

        // todo:Bucar el email
        Optional<UserEntity> userExisting = userRepository.findByEmail(userDto.getEmail());
        if (userExisting.isEmpty()) {
            throw new EmailNotFoundException(String.format("¡El email %s no esta registrado!", userDto.getEmail()));
        }
        // todo:verificar las contraseñas con bcrypt
        if (!passwordEncoder.matches(userDto.getPassword(), userExisting.get().getPassword())) {
            throw new PasswordMismatchException("¡Hubo un error en las credenciales!");
        }
        // todo: generar el token
        String token = jwtService.generateToken(userExisting.get());
        // todo: devolver respuesta con token
        return AuthResponse
                .builder()
                .status(200)
                .message("¡Te haz logeado correctamente!")
                .userEntity(userExisting.get())
                .token(token)
                .build();
    }

}
