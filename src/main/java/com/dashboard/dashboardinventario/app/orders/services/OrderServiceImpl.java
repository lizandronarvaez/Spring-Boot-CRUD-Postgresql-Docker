package com.dashboard.dashboardinventario.app.orders.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    @Override
    public OrderEntity createOrder(OrderDto orderDto) {

        // Creamos la orden
        final OrderEntity orderEntity = OrderEntity
                .builder()
                .clientEntity(orderDto.getClient())
                .total(Double.parseDouble(orderDto.getTotal()))
                .build();

        // Guardar la orden principal para obtener el ID generado
        OrderEntity saveOrderEntity = orderRepository.save(orderEntity);
        // Obtener el ID de la orden principal
        Integer orderId = orderEntity.getId();
        // Obtener la lista de productos
        List<OrderItemEntity> orderDetails = new ArrayList<>();
        orderDto.getOrder().forEach(product -> {
            System.out.println("Datos de cada producto: " + product);
            Optional<OrderItemEntity> existingOrderItem = orderItemRepository.findById(product.getId());
            if (existingOrderItem.isEmpty()) {
                // Crear un detalle de pedido
                OrderItemEntity orderItemEntity = OrderItemEntity
                        .builder()
                        .order(saveOrderEntity)
                        .product(product)
                        .quantity(product.getQuantity())
                        .price(product.getPrice())
                        .build();

                System.out.println("DATOS DE CADA ORDERN ENTITY DESPUES DE CREARLA:" + orderItemEntity);
                orderDetails.add(orderItemEntity);
            } else {
                OrderItemEntity existingOrder = existingOrderItem.get();
                orderDetails.add(existingOrder);
            }
        });

        // Guardar los detalles de la orden en la base de datos
        orderItemRepository.saveAll(orderDetails);

        // Asignar los detalles de la orden a la orden principal
        orderEntity.setDetails(orderDetails);

        // Actualizar la orden principal en la base de datos para incluir los detalles
        orderRepository.save(orderEntity);

        return orderEntity;
    }

    @Transactional(readOnly = true)
    @Override
    public List<OrderEntity> getAllOrders() {
        return (List<OrderEntity>) orderRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public OrderEntity getOrderById(Integer id) {
        return orderRepository.findById(id).orElse(null);
    }

}
