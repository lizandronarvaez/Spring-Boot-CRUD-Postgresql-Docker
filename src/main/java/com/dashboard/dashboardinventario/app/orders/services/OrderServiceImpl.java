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

        // Obtener el cliente
        ClientEntity clientEntity = orderDto.getClient();
        // Obtener el total
        String totalOrder = orderDto.getTotal();

        // Creamos la orden
        OrderEntity[] orderEntity = new OrderEntity[1]; // Array de un solo elemento
        orderEntity[0] = new OrderEntity();
        orderEntity[0].setClientEntity(clientEntity);
        orderEntity[0].setTotal(Double.parseDouble(totalOrder));

        // Guardar la orden principal para obtener el ID generado
        orderEntity[0] = orderRepository.save(orderEntity[0]);

        // Obtener el ID de la orden principal
        Integer orderId = orderEntity[0].getId();

        // Obtener la lista de productos
        List<OrderItemEntity> orderDetails = new ArrayList<>();
        orderDto.getOrder().forEach(product -> {

            // Crear un detalle de pedido
            OrderItemEntity orderItemEntity = OrderItemEntity.builder()
                    .id(orderId)
                    .order(orderEntity[0])
                    .product(product)
                    .quantity(product.getQuantity())
                    .price(product.getPrice())
                    .build();

            orderDetails.add(orderItemEntity);
        });

        // Guardar los detalles de la orden en la base de datos
        orderItemRepository.saveAll(orderDetails);

        // Asignar los detalles de la orden a la orden principal
        orderEntity[0].setDetails(orderDetails);

        // Actualizar la orden principal en la base de datos para incluir los detalles
        orderRepository.save(orderEntity[0]);

        return orderEntity[0];
    }

    @Override
    public List<OrderEntity> getAllOrders() {
        return (List<OrderEntity>) orderRepository.findAll();
    }

    @Override
    public OrderEntity getOrderById(Integer id) {
        return orderRepository.findById(id).orElse(null);
    }

}
