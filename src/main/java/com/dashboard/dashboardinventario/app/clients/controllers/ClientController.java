package com.dashboard.dashboardinventario.app.clients.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dashboard.dashboardinventario.app.clients.models.dto.ClientDto;
import com.dashboard.dashboardinventario.app.clients.services.IClientService;

import jakarta.validation.Valid;

@CrossOrigin(origins = { "http://localhost:5173", "http://localhost:5174/",
        "https://app-protein-shop-react.vercel.app/","https://crm-frontend-alpha.vercel.app/" })
@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    private final IClientService clientService;

    // Crear un cliente
    @PostMapping()
    public ResponseEntity<?> create(@Valid @RequestBody ClientDto clientDto) {
        try {
            return new ResponseEntity<>(clientService.saveClient(clientDto), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Mostrar todos los clientes
    @GetMapping()
    public ResponseEntity<?> findAll() {
        try {
            return new ResponseEntity<>(clientService.findAllClients(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Mostrar un cliente
    @GetMapping("/{id}")
    public ResponseEntity<?> find(@PathVariable Integer id) {
        try {
            return new ResponseEntity<>(clientService.findClientById(id), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody ClientDto clientDto, @PathVariable Integer id) {
        try {
            return new ResponseEntity<>(clientService.updateClient(clientDto, id), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            return new ResponseEntity<>(clientService.deleteClient(id), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());        }
    }

    // Login cliente
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody ClientDto clientDto) {
        try {
            return new ResponseEntity<>(clientService.loginClient(clientDto), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
