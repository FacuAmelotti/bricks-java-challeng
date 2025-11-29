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
@Cacheable(
        value = "products",
        key = "'all'",
        // Solo cacheamos cuando NO hay filtros ni paginación
        condition = "#filter == null || " +
                "(#filter.name == null && #filter.minPrice == null && #filter.maxPrice == null && " +
                "#filter.categoryId == null && #filter.minStock == null && #filter.maxStock == null && " +
                "#filter.page == null && #filter.size == null)"
)
public List<ProductResponse> findAll(ProductFilter filter) {
    System.out.println(">>> [CACHE TEST] Ejecutando findAll() en ProductServiceImpl (SIN usar caché)");

    // Detectar si no hay filtros "reales"
    boolean filtroVacio = (filter == null)
            || (filter.getName() == null
            && filter.getMinPrice() == null
            && filter.getMaxPrice() == null
            && filter.getCategoryId() == null
            && filter.getMinStock() == null
            && filter.getMaxStock() == null
            && filter.getPage() == null
            && filter.getSize() == null);

    // Siempre traemos de la base y después filtramos en memoria (para el challenge va sobrado)
    List<Product> source = productRepository.findAll();

    List<Product> filtrados;

    if (filtroVacio) {
        // Sin filtros → devolvemos todo (y este caso se cachea)
        filtrados = source;
    } else {
        String name = filter.getName();
        BigDecimal minPrice = filter.getMinPrice();
        BigDecimal maxPrice = filter.getMaxPrice();
        Long categoryId = filter.getCategoryId();
        Integer minStock = filter.getMinStock();
        Integer maxStock = filter.getMaxStock();

        filtrados = source.stream()
                .filter(p -> {
                    boolean ok = true;

                    // Name contiene (case-insensitive)
                    if (name != null && !name.isBlank()) {
                        ok = ok
                                && p.getName() != null
                                && p.getName().toLowerCase().contains(name.toLowerCase());
                    }

                    // Precio mínimo
                    if (minPrice != null) {
                        ok = ok && p.getPrice() != null
                                && p.getPrice().compareTo(minPrice) >= 0;
                    }

                    // Precio máximo
                    if (maxPrice != null) {
                        ok = ok && p.getPrice() != null
                                && p.getPrice().compareTo(maxPrice) <= 0;
                    }

                    // Categoría
                    if (categoryId != null) {
                        ok = ok && p.getCategory() != null
                                && categoryId.equals(p.getCategory().getId());
                    }

                    // Stock mínimo
                    if (minStock != null) {
                        ok = ok && p.getStock() != null
                                && p.getStock() >= minStock;
                    }

                    // Stock máximo
                    if (maxStock != null) {
                        ok = ok && p.getStock() != null
                                && p.getStock() <= maxStock;
                    }

                    return ok;
                })
                .toList();
    }

    // ===== Paginación en memoria =====
    int page = 0;
    int size = filtrados.size(); // si no se especifica, devolvemos todo

    if (filter != null) {
        if (filter.getPage() != null) {
            page = Math.max(0, filter.getPage());
        }
        if (filter.getSize() != null) {
            size = Math.max(1, filter.getSize());
        }
    }

    int fromIndex = page * size;
    if (fromIndex >= filtrados.size()) {
        // Página fuera de rango → lista vacía
        return List.of();
    }

    int toIndex = Math.min(fromIndex + size, filtrados.size());

    return filtrados.subList(fromIndex, toIndex)
            .stream()
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
