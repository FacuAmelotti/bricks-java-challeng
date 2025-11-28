package com.bricks.challenge.controller;

import com.bricks.challenge.dto.product.ProductFilter;
import com.bricks.challenge.dto.product.ProductRequest;
import com.bricks.challenge.dto.product.ProductResponse;
import com.bricks.challenge.service.ProductService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import java.util.List;

@Tag(
        name = "Products",
        description = "Operaciones CRUD sobre productos internos"
)
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // ===============================
    // GET /api/products
    // ===============================
    @Operation(
            summary = "Listado de productos",
            description = "Devuelve todos los productos. Permite filtros opcionales por precio, stock y categoría."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado devuelto correctamente")
    })
    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAll(ProductFilter filter) {
        return ResponseEntity.ok(productService.findAll(filter));
    }

    // ===============================
    // GET /api/products/{id}
    // ===============================
    @Operation(
            summary = "Obtener producto por ID",
            description = "Devuelve un producto existente identificado por su ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto encontrado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    // ===============================
    // POST /api/products
    // ===============================
    @Operation(
            summary = "Crear producto",
            description = "Crea un nuevo producto a partir de un ProductRequest."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Producto creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos en la request")
    })
    @PostMapping
    public ResponseEntity<ProductResponse> create(@RequestBody @Valid ProductRequest request) {
        ProductResponse created = productService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // ===============================
    // PUT /api/products/{id}
    // ===============================
    @Operation(
            summary = "Actualizar producto",
            description = "Actualiza un producto existente identificado por su ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos en la request"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(
            @PathVariable Long id,
            @RequestBody @Valid ProductRequest request
    ) {
        ProductResponse updated = productService.update(id, request);
        return ResponseEntity.ok(updated);
    }

    // ===============================
    // DELETE /api/products/{id}
    // ===============================
    @Operation(
            summary = "Eliminar producto",
            description = "Elimina un producto existente identificado por su ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Producto eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
