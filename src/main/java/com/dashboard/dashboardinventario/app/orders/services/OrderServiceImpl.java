package com.dashboard.dashboardinventario.app.orders.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.dashboard.dashboardinventario.app.clients.models.entities.ClientEntity;
import com.dashboard.dashboardinventario.app.orders.models.dto.OrderDto;
import com.dashboard.dashboardinventario.app.orders.models.entities.OrderEntity;
import com.dashboard.dashboardinventario.app.orders.models.entities.OrderItemEntity;
import com.dashboard.dashboardinventario.app.orders.repository.OrderItemRepository;
import com.dashboard.dashboardinventario.app.orders.repository.OrderRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    public OrderEntity createOrder(OrderDto orderDto) {

        // Obtener el client
        ClientEntity clientEntity = orderDto.getClient();
        // Obetener el total
        String totalOrder = orderDto.getTotal();

        // Creamos la orden
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setClientEntity(clientEntity);
        orderEntity.setTotal(Double.parseDouble(totalOrder));

        // Obtener la lista de productos
        List<OrderItemEntity> orderDetails = new ArrayList<>();
        orderDto.getOrder().forEach(product -> {

            // Crea un detalle de pedido
            OrderItemEntity orderItemEntity = OrderItemEntity.builder()
                    .product(product)
                    .quantity(product.getQuantity())
                    .price(product.getPrice())
                    .build();

            orderDetails.add(orderItemEntity);
        });

        orderEntity.setDetails(orderDetails);
        System.out.println(orderEntity);
        orderRepository.save(orderEntity);
        orderItemRepository.saveAll(orderDetails);
        return orderEntity;
    }

}
