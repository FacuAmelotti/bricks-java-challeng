package com.bricks.challenge.service;

import com.bricks.challenge.dto.product.ProductFilter;
import com.bricks.challenge.dto.product.ProductRequest;
import com.bricks.challenge.dto.product.ProductResponse;
import com.bricks.challenge.entity.Category;
import com.bricks.challenge.entity.Product;
import com.bricks.challenge.exception.ResourceNotFoundException;
import com.bricks.challenge.mapper.ProductMapper;
import com.bricks.challenge.repository.ProductRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;


@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository,
                              CategoryService categoryService,
                              ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.productMapper = productMapper;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "products", key = "'all'")
    public List<ProductResponse> findAll(ProductFilter filter) {
        System.out.println(">>> [CACHE TEST] Ejecutando findAll() en ProductServiceImpl (SIN usar caché)");

        // 1) Detectar si el filtro está vacío (sin parámetros útiles)
        boolean filtroVacio = (filter == null)
                || (filter.getMinPrice() == null
                && filter.getMaxPrice() == null
                && filter.getCategoryId() == null);

        List<Product> products;

        if (filtroVacio) {
            // Caso común: GET /api/products sin filtros → traemos todo
            products = productRepository.findAll();
        } else {
            BigDecimal minPrice = filter.getMinPrice();
            BigDecimal maxPrice = filter.getMaxPrice();
            Long categoryId = filter.getCategoryId();

            products = productRepository.findAll().stream()
                    .filter(p -> {
                        boolean ok = true;

                        if (minPrice != null) {
                            ok = ok && p.getPrice().compareTo(minPrice) >= 0;
                        }
                        if (maxPrice != null) {
                            ok = ok && p.getPrice().compareTo(maxPrice) <= 0;
                        }
                        if (categoryId != null && p.getCategory() != null) {
                            ok = ok && categoryId.equals(p.getCategory().getId());
                        }

                        return ok;
                    })
                    .toList();
        }

        return products.stream()
                .map(productMapper::toResponse)
                .toList();
    }



    @Override
    @Transactional(readOnly = true)
    public ProductResponse findById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product with id " + id + " not found"));

        return productMapper.toResponse(product);
    }

    @Override
    @Transactional
    @CacheEvict(value = "products", key = "'all'")
    public ProductResponse create(ProductRequest request) {
        Category category = categoryService.getEntityById(request.getCategoryId());

        Product product = productMapper.toEntity(request, category);
        Product saved = productRepository.save(product);

        return productMapper.toResponse(saved);
    }

    @Override
    @Transactional
    @CacheEvict(value = "products", key = "'all'")
    public ProductResponse update(Long id, ProductRequest request) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product with id " + id + " not found"));

        Category category = categoryService.getEntityById(request.getCategoryId());

        existing.setName(request.getName());
        existing.setPrice(request.getPrice());
        existing.setStock(request.getStock());
        existing.setCategory(category);

        Product updated = productRepository.save(existing);
        return productMapper.toResponse(updated);
    }

    @Override
    @Transactional
    @CacheEvict(value = "products", key = "'all'")
    public void delete(Long id) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product with id " + id + " not found"));

        productRepository.delete(existing);
    }
}
