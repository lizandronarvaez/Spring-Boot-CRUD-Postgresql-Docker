package com.dashboard.dashboardinventario.app.orders.controllers;

import com.dashboard.dashboardinventario.app.orders.models.dto.OrderDto;
import com.dashboard.dashboardinventario.app.orders.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = { "http://localhost:5173", "https://app-protein-shop-react.vercel.app/","https://crm-frontend-alpha.vercel.app/" })
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // CREAR PEDIDO
    @PostMapping()
    public ResponseEntity<?> create(@RequestBody OrderDto orderDto) {
        try {
            return new ResponseEntity<>(orderService.createOrder(orderDto), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping()
    public ResponseEntity<?> findAll() {
        try {
            return new ResponseEntity<>(orderService.getAllOrders(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> find(@PathVariable Integer id) {
        try {
            return new ResponseEntity<>(orderService.getOrderById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // @PutMapping()
    // public ResponseEntity<?> update(@RequestBody dto) {
    // try {
    // //TODO Implement Your Logic To Update Data And Return Result Through
    // ResponseEntity
    // return new ResponseEntity<>("Update Result", HttpStatus.OK);
    // } catch (Exception e) {
    // return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    // }
    // }

    // @DeleteMapping("/{id}")
    // public ResponseEntity<?> delete(@PathVariable Integer id) {
    // try {
    // //TODO Implement Your Logic To Destroy Data And Return Result Through
    // ResponseEntity
    // return new ResponseEntity<>("Destroy Result", HttpStatus.OK);
    // } catch (Exception e) {
    // return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    // }
    // }
}
