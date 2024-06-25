package com.dashboard.dashboardinventario.app.orders.models.entities;

import java.time.LocalDate;
import java.util.List;
import com.dashboard.dashboardinventario.app.clients.models.entities.ClientEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class OrderEntity {

    // Id del pedido
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    // Detalles del pedido
    @OneToMany(mappedBy = "order", cascade = CascadeType.PERSIST)
    private List<OrderItemEntity> details;

    // datos del cliente
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private ClientEntity clientEntity;

    // fecha de creacion del pedido
    @Column(name = "create_at")
    @Temporal(TemporalType.DATE)
    private LocalDate createdAt;

    // total del pedido
    private double total;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDate.now();
    }
}
