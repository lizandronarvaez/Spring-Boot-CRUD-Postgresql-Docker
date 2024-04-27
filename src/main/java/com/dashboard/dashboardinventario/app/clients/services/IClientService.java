package com.dashboard.dashboardinventario.app.clients.services;

import java.util.List;

import com.dashboard.dashboardinventario.app.clients.controllers.AuthResponse;
import com.dashboard.dashboardinventario.app.clients.models.dto.ClientDto;
import com.dashboard.dashboardinventario.app.clients.models.entities.ClientEntity;

public interface IClientService {

    AuthResponse saveClient(ClientDto clientDto);

    List<ClientEntity> findAllClients();

    AuthResponse findClientById(Integer id);

    AuthResponse updateClient(ClientDto clientDto, Integer id);

    AuthResponse deleteClient(Integer id);

    AuthResponse loginClient(ClientDto clientDto);
}
