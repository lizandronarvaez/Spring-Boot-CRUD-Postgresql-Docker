package com.dashboard.dashboardinventario.clients.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.dashboard.dashboardinventario.clients.models.entities.ClientEntity;
import java.util.Optional;

@Repository
public interface ClientsRepository extends CrudRepository<ClientEntity, Integer> {

    Optional<ClientEntity> findByEmail(String email);
}
