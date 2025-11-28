package com.bricks.challenge.integration;

import com.bricks.challenge.dto.product.ProductResponse;

import java.util.List;

public interface FakeStoreIntegrationService {

    /**
     * Importa productos de la categoría "electronics" desde FakeStore,
     * los adapta a nuestro dominio, los persiste y devuelve los ProductResponse resultantes.
     */
    List<ProductResponse> importElectronicsProducts();
}
