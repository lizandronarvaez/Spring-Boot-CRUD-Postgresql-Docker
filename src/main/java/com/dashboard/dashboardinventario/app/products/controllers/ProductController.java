package com.dashboard.dashboardinventario.app.products.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.dashboard.dashboardinventario.app.products.models.dto.ProductDto;
import com.dashboard.dashboardinventario.app.products.services.IProductService;

@CrossOrigin(origins = { "http://localhost:5173", "http://localhost:5174", "https://app-protein-shop-react.vercel.app/",
        "https://crm-frontend-alpha.vercel.app/" })
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final IProductService productService;

    // Crear producto
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> create(
            @RequestParam(value = "imageProduct", required = false) MultipartFile file,
            @RequestParam("fullname") String fullname,
            @RequestParam("description") String description,
            @RequestParam("price") double price,
            @RequestParam("quantity") int quantity,
            @RequestParam("category") String category) {

        try {
            ProductDto productDto = ProductDto
                    .builder()
                    .fullname(fullname)
                    .description(description)
                    .price(price)
                    .quantity(quantity)
                    .category(category)
                    .build();

            return new ResponseEntity<>(productService.saveProduct(file, productDto), HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Mostrar todos los productos
    @GetMapping()
    public ResponseEntity<?> findAll() {
        try {
            return new ResponseEntity<>(productService.findAllProduct(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Mostrar un producto
    @GetMapping("/{id}")
    public ResponseEntity<?> find(@PathVariable Integer id) {
        try {
            return new ResponseEntity<>(productService.findProductById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Buscar un producto por una palabra
    @PostMapping("/finds_products")
    public ResponseEntity<?> findProductByWord(@RequestBody String word) {
        try {
            return new ResponseEntity<>(productService.findProductByWord(word), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Actualizar un producto
    @PutMapping(path = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> update(
            @PathVariable Integer id,
            @RequestParam(value = "imageProduct", required = false) MultipartFile file,
            @RequestParam("fullname") String fullname,
            @RequestParam("description") String description,
            @RequestParam("price") double price,
            @RequestParam("quantity") int quantity,
            @RequestParam("category") String category) {
        try {
            ProductDto productDto = ProductDto
                    .builder()
                    .fullname(fullname)
                    .description(description)
                    .price(price)
                    .quantity(quantity)
                    .category(category)
                    .build();

            return new ResponseEntity<>(productService.updateProduct(file, productDto, id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Eliminar un producto
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            return new ResponseEntity<>(productService.deleteProduct(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
