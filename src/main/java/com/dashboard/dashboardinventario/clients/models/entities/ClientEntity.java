package com.dashboard.dashboardinventario.clients.models.entities;

import java.util.Date;
import java.util.List;

import com.dashboard.dashboardinventario.orders.models.entities.OrderEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "clients", uniqueConstraints = { @UniqueConstraint(columnNames = { "email" }) })
public class ClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private String fullname;
    
    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String phone;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false,name = "postal_code")
    private String postalcode;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String country;

    @Column(name = "create_at")
    @Temporal(TemporalType.DATE)
    private Date createdAt;

    @OneToMany(mappedBy = "clientEntity", cascade = CascadeType.PERSIST)
    private List<OrderEntity> orders;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }
}
