package com.dashboard.dashboardinventario.products.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.dashboard.dashboardinventario.products.models.entity.ProductEntity;

@Repository
public interface ProductRepository extends CrudRepository<ProductEntity,Integer> {

}
