package com.dashboard.dashboardinventario.app.orders.repository;

import org.springframework.data.repository.CrudRepository;

import com.dashboard.dashboardinventario.app.orders.models.entities.OrderItemEntity;

public interface OrderItemRepository extends CrudRepository<OrderItemEntity,Integer> {
 
}
