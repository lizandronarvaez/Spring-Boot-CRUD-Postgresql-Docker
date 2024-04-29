package com.dashboard.dashboardinventario.app.clients.services;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dashboard.dashboardinventario.app.clients.controllers.AuthResponse;
import com.dashboard.dashboardinventario.app.clients.models.dto.ClientDto;
import com.dashboard.dashboardinventario.app.clients.models.entities.ClientEntity;
import com.dashboard.dashboardinventario.app.clients.repository.ClientsRepository;
import com.dashboard.dashboardinventario.security.auth.validation.EmailNotFoundException;
import com.dashboard.dashboardinventario.security.auth.validation.PasswordMismatchException;
import com.dashboard.dashboardinventario.security.jwt.service.JwtService;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements IClientService {

    private final ClientsRepository clientRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    // Crea un cliente
    @SuppressWarnings("null")
    @Transactional
    @Override
    public AuthResponse saveClient(ClientDto clientDto) {
        Optional<ClientEntity> clientExisting = clientRepository.findByEmail(clientDto.getEmail());
        if (clientExisting.isPresent())
            throw new DuplicateKeyException(String.format("¡El email %s ya esta registrado!", clientDto.getEmail()));

        ClientEntity clientEntity = ClientEntity.builder()
                .fullname(clientDto.getFullname())
                .email(clientDto.getEmail())
                .password(passwordEncoder.encode(clientDto.getPassword()))
                .phone(clientDto.getPhone())
                .address(clientDto.getAddress())
                .postalcode(clientDto.getPostalcode())
                .city(clientDto.getCity())
                .country(clientDto.getCountry())
                .build();
        clientRepository.save(clientEntity);
        return AuthResponse
                .builder()
                .status(HttpStatus.OK.value())
                .client(clientEntity)
                .message("¡Cliente creado correctamente!")
                .build();
    }

    // Lista todos los clientes
    @Transactional(readOnly = true)
    @Override
    public List<ClientEntity> findAllClients() {
        return (List<ClientEntity>) clientRepository.findAll();
    }

    // Busca un cliente
    @Override
    @Transactional(readOnly = true)
    public AuthResponse findClientById(Integer id) {
        @SuppressWarnings("null")
        Optional<ClientEntity> clientExisting = clientRepository.findById(id);
        if (clientExisting.isEmpty()) {
            AuthResponse responseError = AuthResponse.builder()
                    .status(HttpStatus.NOT_FOUND.value())
                    .message(String.format("¡El cliente con id %s no existe!", id))
                    .build();
            return responseError;
        }
        return AuthResponse
                .builder()
                .status(HttpStatus.OK.value())
                .client(clientExisting.get())
                .build();
    }

    // Actualiza un cliente
    @SuppressWarnings("null")
    @Transactional
    @Override
    public AuthResponse updateClient(ClientDto clientDto, Integer id) {
        // Busca el cliente
        Optional<ClientEntity> clientExisting = clientRepository.findById(id);
        if (clientExisting.isEmpty()) {
            return AuthResponse.builder()
                    .status(HttpStatus.NOT_FOUND.value())
                    .message(String.format("¡El cliente con id %s no existe!", id))
                    .build();
        }
        // Actualiza el cliente
        clientExisting.get().setFullname(clientDto.getFullname());
        clientExisting.get().setEmail(clientDto.getEmail());
        clientExisting.get().setPhone(clientDto.getPhone());
        clientExisting.get().setAddress(clientDto.getAddress());
        clientExisting.get().setPostalcode(clientDto.getPostalcode());
        clientExisting.get().setCity(clientDto.getCity());
        clientExisting.get().setCountry(clientDto.getCountry());
        clientRepository.save(clientExisting.get());

        return AuthResponse
                .builder()
                .status(HttpStatus.OK.value())
                .client(clientExisting.get())
                .message("¡Cliente actualizado correctamente!")
                .build();
    }

    // Elimina un cliente
    @SuppressWarnings("null")
    @Transactional
    @Override
    public AuthResponse deleteClient(Integer id) {
        // Busca el cliente
        Optional<ClientEntity> clientExisting = clientRepository.findById(id);
        if (clientExisting.isEmpty()) {
            return AuthResponse.builder()
                    .status(HttpStatus.NOT_FOUND.value())
                    .message(String.format("¡El cliente con id %s no existe!", id))
                    .build();
        }

        clientRepository.deleteById(id);
        return AuthResponse
                .builder()
                .status(HttpStatus.OK.value())
                .client(clientExisting.get())
                .message("¡Cliente eliminado correctamente!")
                .build();
    }

    @Override
    public AuthResponse loginClient(ClientDto clientDto) {
        Optional<ClientEntity> clientExisting = clientRepository.findByEmail(clientDto.getEmail());
        if (clientExisting.isEmpty()) {
            throw new EmailNotFoundException(String.format("¡El email %s no esta registrado!", clientDto.getEmail()));
        }
        if (!passwordEncoder.matches(clientDto.getPassword(), clientExisting.get().getPassword())) {
            throw new PasswordMismatchException("¡Hubo un error en las credenciales!");
        }
        return AuthResponse
                .builder()
                .status(200)
                .message("¡Te haz logeado correctamente!")
                .token(jwtService.generateToken(clientExisting.get()))
                .client(clientExisting.get())
                .build();
    }

}
