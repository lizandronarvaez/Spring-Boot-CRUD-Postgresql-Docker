package com.dashboard.dashboardinventario.products.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dashboard.dashboardinventario.products.models.entity.CategoryEntity;

@Repository
public interface CategoryRepository extends CrudRepository<CategoryEntity,Integer> {
    Optional<CategoryEntity> findByName( String name);
}