package com.dashboard.dashboardinventario.security.auth.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.dashboard.dashboardinventario.security.auth.models.entity.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Integer> {

    Optional<UserEntity> findByEmail(String email);
}
