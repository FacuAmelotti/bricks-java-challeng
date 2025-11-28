package com.bricks.challenge.integration;

import com.bricks.challenge.dto.product.ProductResponse;

import java.util.List;

/**
 * Define la integración con FakeStore.
 * Separa el contrato (interfaz) de la implementación concreta.
 */
public interface FakeStoreIntegrationService {

    /**
     * Importa productos de la categoría "electronics" desde FakeStore,
     * los adapta al dominio interno, los persiste y devuelve la lista
     * resultante como ProductResponse.
     */
    List<ProductResponse> importElectronicsProducts();
}
