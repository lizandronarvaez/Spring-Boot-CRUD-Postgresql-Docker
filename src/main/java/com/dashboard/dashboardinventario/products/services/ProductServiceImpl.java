package com.dashboard.dashboardinventario.products.services;

import com.dashboard.dashboardinventario.products.controllers.AuthResponse;
import com.dashboard.dashboardinventario.products.models.dto.ProductDto;
import com.dashboard.dashboardinventario.products.models.entity.CategoryEntity;
import com.dashboard.dashboardinventario.products.models.entity.ProductEntity;
import com.dashboard.dashboardinventario.products.repository.CategoryRepository;
import com.dashboard.dashboardinventario.products.repository.ProductRepository;
import com.dashboard.dashboardinventario.uploads.service.FileUploadServiceImpl;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final FileUploadServiceImpl fileUpload;

    @SuppressWarnings("null")
    @Transactional
    @Override
    public AuthResponse saveProduct(MultipartFile file, ProductDto productDto) {

        // Limpieza de datos en el servicio
        String fullname = productDto.getFullname().trim().toLowerCase();
        String description = productDto.getDescription().trim().toLowerCase();
        Double price = productDto.getPrice();
        Integer quantity = productDto.getQuantity();
        String imageProduct = file != null && !file.isEmpty() ? fileUpload.uploadFile(file, "products").toLowerCase()
                : "";
        String categoryName = productDto.getCategory().toLowerCase().trim();

        // BUSCO LA CATEGORIA
        Optional<CategoryEntity> categoryOptional = categoryRepository.findByName(categoryName);
        // Si la categoría no existe, la creamos
        CategoryEntity newCategory = null;
        if (categoryOptional.isEmpty()) {
            newCategory = CategoryEntity.builder()
                    .name(categoryName)
                    .build();
            categoryRepository.save(newCategory);
        } else {
            newCategory = categoryOptional.get();
        }

        // Crea un nuevo producto
        ProductEntity productEntity = ProductEntity
                .builder()
                .fullname(fullname)
                .description(description)
                .price(price)
                .quantity(quantity)
                .imageProduct(imageProduct)
                .category(newCategory)
                .build();

        // Agrega el producto en la categoria
        if (newCategory != null) {
            newCategory.getProduct().add(productEntity);
        }
        // Guarda el producto en la base de datos
        productRepository.save(productEntity);
        categoryRepository.save(newCategory);

        // Crear la respuesta de autenticación
        AuthResponse response = AuthResponse
                .builder()
                .status(HttpStatus.OK.value())
                .message("¡Producto creado correctamente!")
                .product(productEntity)
                .build();
        return response;
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductEntity> findAllProduct() {
        return (List<ProductEntity>) productRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public AuthResponse findProductById(Integer id) {
        Optional<ProductEntity> productExisting = productRepository.findById(id);
        if (productExisting.isPresent()) {
            return AuthResponse.builder()
                    .status(HttpStatus.OK.value())
                    .product(productExisting.get())
                    .build();
        }
        return AuthResponse
                .builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(String.format("¡El producto con id %s no existe!", id))
                .build();
    }

    @Transactional
    @Override
    public AuthResponse updateProduct(MultipartFile file, ProductDto productDto, Integer id) {
        // Limpieza de datos en el servicio
        String fullname = productDto.getFullname().trim().toLowerCase();
        String description = productDto.getDescription().trim().toLowerCase();
        Double price = productDto.getPrice();
        Integer quantity = productDto.getQuantity();
        //
        AuthResponse product = findProductById(id);
        Optional<CategoryEntity> categoryExisting = categoryRepository.findByName(productDto.getCategory());
        CategoryEntity categoryUpdate;
        if (product.getStatus().equals(200)) {
            // Verifica la categoria
            if (categoryExisting.isPresent()) {
                categoryUpdate = categoryExisting.get();
            } else {
                categoryUpdate = CategoryEntity.builder().name(productDto.getCategory()).build();
                categoryUpdate = categoryRepository.save(categoryUpdate);
            }
            product.getProduct().setFullname(fullname);
            product.getProduct().setDescription(description);
            product.getProduct().setPrice(price);
            product.getProduct().setQuantity(quantity);
            product.getProduct().setCategory(categoryUpdate);

            // Verifica la imagen
            if (file != null && !file.isEmpty()) {
                product.getProduct().setImageProduct(fileUpload.uploadFile(file, "products"));
            } else {
                product.getProduct().setImageProduct(product.getProduct().getImageProduct());
            }

            // Guarda el producto
            ProductEntity productUpdate = productRepository.save(product.getProduct());

            // Retorna la respuesta
            return AuthResponse.builder()
                    .status(HttpStatus.OK.value())
                    .message("¡Producto actualizado correctamente!")
                    .product(productUpdate)
                    .build();
        }
        return AuthResponse
                .builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message((product.getMessage() != null)
                        ? product.getMessage()
                        : "¡Hubo un error al actualizar el producto!")
                .build();
    }

    @SuppressWarnings("null")
    @Transactional
    @Override
    public AuthResponse deleteProduct(Integer id) {
        // Busca el product
        AuthResponse product = findProductById(id);
        // Verifica el producto
        if (product.getStatus() != 200) {
            return AuthResponse.builder()
                    .status(HttpStatus.NOT_FOUND.value())
                    .message(product.getMessage())
                    .build();
        }
        // Elimina el producto
        productRepository.delete(product.getProduct());

        // Retorna la respuesta
        return AuthResponse.builder()
                .status(HttpStatus.OK.value())
                .product(product.getProduct())
                .message("¡Producto eliminado correctamente!")
                .build();
    }

}
