package com.bricks.challenge.mapper;

import com.bricks.challenge.dto.category.CategoryResponse;
import com.bricks.challenge.dto.product.ProductRequest;
import com.bricks.challenge.dto.product.ProductResponse;
import com.bricks.challenge.entity.Category;
import com.bricks.challenge.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductMapperTest {

    private CategoryMapper categoryMapper;
    private ProductMapper productMapper;

    @BeforeEach
    void setUp() {
        categoryMapper = new CategoryMapper();
        productMapper = new ProductMapper(categoryMapper);
    }

    @Test
    void toResponse_whenEntityIsNull_returnsNull() {
        // when
        ProductResponse result = productMapper.toResponse(null);

        // then
        assertNull(result);
    }

    @Test
    void toResponse_whenEntityIsValid_mapsAllFields() {
        // given
        Category category = new Category();
        category.setId(1L);
        category.setTitle("Electronics");

        Product entity = new Product();
        entity.setId(10L);
        entity.setName("Test Product");
        entity.setPrice(new BigDecimal("99.99"));
        entity.setStock(5);
        entity.setCategory(category);

        // when
        ProductResponse result = productMapper.toResponse(entity);

        // then
        assertNotNull(result);
        assertEquals(10L, result.getId());
        assertEquals("Test Product", result.getName());
        assertEquals(new BigDecimal("99.99"), result.getPrice());
        assertEquals(5, result.getStock());

        CategoryResponse categoryResponse = result.getCategory();
        assertNotNull(categoryResponse);
        assertEquals(1L, categoryResponse.getId());
        assertEquals("Electronics", categoryResponse.getTitle());
    }

    @Test
    void toEntity_whenRequestIsNull_returnsNull() {
        // when
        Product result = productMapper.toEntity(null, null);

        // then
        assertNull(result);
    }

    @Test
    void toEntity_whenRequestAndCategoryAreValid_mapsFieldsCorrectly() {
        // given
        ProductRequest request = new ProductRequest();
        request.setName("New Product");
        request.setPrice(new BigDecimal("123.45"));
        request.setStock(7);
        request.setCategoryId(99L); // el mapper no la usa, se pasa la Category

        Category category = new Category();
        category.setId(2L);
        category.setTitle("Gadgets");

        // when
        Product result = productMapper.toEntity(request, category);

        // then
        assertNotNull(result);
        assertEquals("New Product", result.getName());
        assertEquals(new BigDecimal("123.45"), result.getPrice());
        assertEquals(7, result.getStock());
        assertNotNull(result.getCategory());
        assertEquals(2L, result.getCategory().getId());
        assertEquals("Gadgets", result.getCategory().getTitle());
    }
}
