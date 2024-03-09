package com.dashboard.dashboardinventario.clients.services;

import com.dashboard.dashboardinventario.clients.controllers.AuthResponse;
import com.dashboard.dashboardinventario.clients.models.dto.ClientDto;
import com.dashboard.dashboardinventario.clients.models.entities.ClientEntity;
import com.dashboard.dashboardinventario.clients.repository.ClientsRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements IClientService {

    private final ClientsRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    // Crea un cliente
    @Transactional
    @Override
    public AuthResponse saveClient(ClientDto clientDto) {
        Optional<ClientEntity> clientExisting = clientRepository.findByEmail(clientDto.getEmail());
        if (clientExisting.isPresent()) {
            throw new DuplicateKeyException(String.format("¡El email %s ya esta registrado!", clientDto.getEmail()));
        }
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
        AuthResponse response = AuthResponse
                .builder()
                .status(HttpStatus.OK.value())
                .client(clientEntity)
                .message("¡Cliente creado correctamente!")
                .build();
        return response;
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
        Optional<ClientEntity> clientExisting = clientRepository.findById(id);
        if (clientExisting.isEmpty()) {
            AuthResponse responseError = AuthResponse.builder()
                    .status(HttpStatus.NOT_FOUND.value())
                    .message(String.format("¡El cliente con id %s no existe!", id))
                    .build();
            return responseError;
        }
        AuthResponse authResponse = AuthResponse
                .builder()
                .status(HttpStatus.OK.value())
                .client(clientExisting.get())
                .build();
        return authResponse;
    }

    // Actualiza un cliente
    @Transactional
    @Override
    public AuthResponse updateClient(ClientDto clientDto, Integer id) {
        // Busca el cliente
        Optional<ClientEntity> clientExisting = clientRepository.findById(id);
        if (clientExisting.isEmpty()) {
            AuthResponse responseError = AuthResponse.builder()
                    .status(HttpStatus.NOT_FOUND.value())
                    .message(String.format("¡El cliente con id %s no existe!", id))
                    .build();
            return responseError;
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

        AuthResponse authResponse = AuthResponse
                .builder()
                .status(HttpStatus.OK.value())
                .client(clientExisting.get())
                .message("¡Cliente actualizado correctamente!")
                .build();
        return authResponse;
    }

    // Elimina un cliente
    @Transactional
    @Override
    public AuthResponse deleteClient(Integer id) {
                // Busca el cliente
                Optional<ClientEntity> clientExisting = clientRepository.findById(id);
                if (clientExisting.isEmpty()) {
                    AuthResponse responseError = AuthResponse.builder()
                            .status(HttpStatus.NOT_FOUND.value())
                            .message(String.format("¡El cliente con id %s no existe!", id))
                            .build();
                    return responseError;
                }

                clientRepository.deleteById(id);
                AuthResponse authResponse = AuthResponse
                        .builder()
                        .status(HttpStatus.OK.value())
                        .client(clientExisting.get())
                        .message("¡Cliente eliminado correctamente!")
                        .build();
                return authResponse;
    }

}
