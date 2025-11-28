package com.bricks.challenge.controller;

import com.bricks.challenge.dto.product.ProductFilter;
import com.bricks.challenge.dto.product.ProductRequest;
import com.bricks.challenge.dto.product.ProductResponse;
import com.bricks.challenge.integration.FakeStoreIntegrationService;
import com.bricks.challenge.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final FakeStoreIntegrationService fakeStoreIntegrationService;

    public ProductController(ProductService productService,
                             FakeStoreIntegrationService fakeStoreIntegrationService) {
        this.productService = productService;
        this.fakeStoreIntegrationService = fakeStoreIntegrationService;
    }

    @PostMapping
    public ResponseEntity<ProductResponse> create(@Valid @RequestBody ProductRequest request) {
        ProductResponse response = productService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAll(ProductFilter filter) {
        return ResponseEntity.ok(productService.findAll(filter));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request
    ) {
        return ResponseEntity.ok(productService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // 🚀 Nuevo endpoint para importar electronics desde FakeStore
    @PostMapping("/import/electronics")
    public ResponseEntity<List<ProductResponse>> importElectronics() {
        List<ProductResponse> imported = fakeStoreIntegrationService.importElectronicsProducts();
        return ResponseEntity.status(HttpStatus.CREATED).body(imported);
    }
}
