package com.dashboard.dashboardinventario.app.orders.repository;

import org.springframework.data.repository.CrudRepository;

import com.dashboard.dashboardinventario.app.orders.models.entities.OrderEntity;

public interface OrderRepository extends CrudRepository<OrderEntity,Integer> {

    
}
