package com.dashboard.dashboardinventario.app.products.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.dashboard.dashboardinventario.app.products.models.entity.ProductEntity;

@Repository
public interface ProductRepository extends CrudRepository<ProductEntity,Integer> {

    List<ProductEntity> findByFullname(String fullname);
}
