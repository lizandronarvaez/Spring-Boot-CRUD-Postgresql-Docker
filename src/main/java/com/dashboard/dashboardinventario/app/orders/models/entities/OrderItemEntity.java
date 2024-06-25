package com.dashboard.dashboardinventario.app.orders.models.entities;

import com.dashboard.dashboardinventario.app.products.models.entity.ProductEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_details")
public class OrderItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    // Producto que almacenaremos
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    // Orden que guardaremos
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity order;

    // UNidades de cada producto
    private int quantity;

    // Total del precio
    private double price;
}
