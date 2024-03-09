package com.dashboard.dashboardinventario.clients.services;

import com.dashboard.dashboardinventario.clients.controllers.AuthResponse;
import com.dashboard.dashboardinventario.clients.models.dto.ClientDto;
import com.dashboard.dashboardinventario.clients.models.entities.ClientEntity;
import java.util.List;
public interface IClientService {
    
    AuthResponse saveClient(ClientDto clientDto);

    List<ClientEntity> findAllClients();

    AuthResponse findClientById(Integer id);

    AuthResponse updateClient(ClientDto clientDto, Integer id);

    AuthResponse deleteClient(Integer id);
    
}
