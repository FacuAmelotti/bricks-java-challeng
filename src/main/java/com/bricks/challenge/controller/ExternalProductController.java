package com.bricks.challenge.controller;

import com.bricks.challenge.dto.product.ProductResponse;
import com.bricks.challenge.integration.FakeStoreIntegrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(
        name = "External Products",
        description = "Integración con FakeStore API para importar productos externos"
)
@RestController
@RequestMapping("/api/external/products")
public class ExternalProductController {

    private final FakeStoreIntegrationService fakeStoreIntegrationService;

    public ExternalProductController(FakeStoreIntegrationService fakeStoreIntegrationService) {
        this.fakeStoreIntegrationService = fakeStoreIntegrationService;
    }

    @Operation(
            summary = "Importar productos electrónicos desde FakeStore",
            description = "Consume la API pública FakeStore, adapta los datos al modelo interno y persiste los productos en la base H2."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Productos importados y persistidos correctamente"),
            @ApiResponse(responseCode = "500", description = "Error al consumir FakeStore o al persistir los datos")
    })
    @GetMapping("/electronics")
    public ResponseEntity<List<ProductResponse>> importElectronicsProducts() {
        List<ProductResponse> imported = fakeStoreIntegrationService.importElectronicsProducts();
        return ResponseEntity.status(HttpStatus.CREATED).body(imported);
    }
}
