package com.bricks.challenge.controller;

import com.bricks.challenge.dto.product.ProductResponse;
import com.bricks.challenge.integration.FakeStoreIntegrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/external/products")
public class ExternalProductController {

    private final FakeStoreIntegrationService fakeStoreIntegrationService;

    public ExternalProductController(FakeStoreIntegrationService fakeStoreIntegrationService) {
        this.fakeStoreIntegrationService = fakeStoreIntegrationService;
    }

    // GET /api/external/products/electronics
    @GetMapping("/electronics")
    public ResponseEntity<List<ProductResponse>> importElectronicsProducts() {
        List<ProductResponse> imported = fakeStoreIntegrationService.importElectronicsProducts();
        return ResponseEntity.status(HttpStatus.CREATED).body(imported);
    }
}
