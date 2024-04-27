package com.dashboard.dashboardinventario.app.orders.models.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDTO {
    private Integer id;
    private String fullname;
    private Double price;
    private Integer quantity;

}
