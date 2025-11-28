package com.bricks.challenge.integration;

import com.bricks.challenge.dto.product.ProductResponse;
import com.bricks.challenge.entity.Category;
import com.bricks.challenge.entity.Product;
import com.bricks.challenge.mapper.ProductMapper;
import com.bricks.challenge.repository.CategoryRepository;
import com.bricks.challenge.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class FakeStoreIntegrationServiceImpl implements FakeStoreIntegrationService {

    private static final String ELECTRONICS_URL =
            "https://fakestoreapi.com/products/category/electronics";

    private final RestTemplate restTemplate;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public FakeStoreIntegrationServiceImpl(
            RestTemplate restTemplate,
            CategoryRepository categoryRepository,
            ProductRepository productRepository,
            ProductMapper productMapper
    ) {
        this.restTemplate = restTemplate;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public List<ProductResponse> importElectronicsProducts() {
        FakeStoreProductDto[] externalProducts =
                restTemplate.getForObject(ELECTRONICS_URL, FakeStoreProductDto[].class);

        List<ProductResponse> responses = new ArrayList<>();

        if (externalProducts == null || externalProducts.length == 0) {
            return responses;
        }

        for (FakeStoreProductDto external : externalProducts) {
            // 1) Resolver/crear categoría interna
            String categoryTitle = external.getCategory() != null
                    ? external.getCategory()
                    : "electronics";

            Category category = categoryRepository.findByTitle(categoryTitle)
                    .orElseGet(() -> {
                        Category c = new Category();
                        c.setTitle(categoryTitle);
                        return categoryRepository.save(c);
                    });

            // 2) Construir Product interno
            Product product = new Product();
            product.setName(external.getTitle());
            product.setPrice(external.getPrice() != null ? external.getPrice() : BigDecimal.ZERO);

            Integer stock = 0;
            if (external.getRating() != null && external.getRating().getCount() != null) {
                stock = external.getRating().getCount();
            }
            product.setStock(stock);
            product.setCategory(category);

            // (Opcional) Evitar duplicados por nombre + categoría
            // Podrías agregar una verificación acá si tuvieras un método en el repo.

            Product saved = productRepository.save(product);
            responses.add(productMapper.toResponse(saved));
        }

        return responses;
    }
}
