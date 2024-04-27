package com.dashboard.dashboardinventario.app.orders.models.dto;

import java.util.List;
import com.dashboard.dashboardinventario.app.clients.models.entities.ClientEntity;
import com.dashboard.dashboardinventario.app.products.models.entity.ProductEntity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class OrderDto {

    private ClientEntity client;

    private List<ProductEntity> order;

    private String total;
}
