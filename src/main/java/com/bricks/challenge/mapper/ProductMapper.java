package com.bricks.challenge.mapper;

import com.bricks.challenge.dto.category.CategoryResponse;
import com.bricks.challenge.dto.product.ProductRequest;
import com.bricks.challenge.dto.product.ProductResponse;
import com.bricks.challenge.entity.Category;
import com.bricks.challenge.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    private final CategoryMapper categoryMapper;

    public ProductMapper(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    public ProductResponse toResponse(Product entity) {
        if (entity == null) {
            return null;
        }

        ProductResponse dto = new ProductResponse();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setPrice(entity.getPrice());
        dto.setStock(entity.getStock());

        Category category = entity.getCategory();
        CategoryResponse categoryResponse = categoryMapper.toResponse(category);
        dto.setCategory(categoryResponse);

        return dto;
    }

    /**
     * Convierte un ProductRequest en Product.
     *
     * La Category ya viene resuelta desde el service.
     */
    public Product toEntity(ProductRequest request, Category category) {
        if (request == null) {
            return null;
        }

        Product entity = new Product();
        entity.setName(request.getName());
        entity.setPrice(request.getPrice());
        entity.setStock(request.getStock());
        entity.setCategory(category);

        return entity;
    }
}
