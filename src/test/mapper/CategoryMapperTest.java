package com.bricks.challenge.mapper;

import com.bricks.challenge.dto.category.CategoryResponse;
import com.bricks.challenge.entity.Category;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryMapperTest {

    private final CategoryMapper categoryMapper = new CategoryMapper();

    @Test
    void toResponse_whenEntityIsNull_returnsNull() {
        // when
        CategoryResponse result = categoryMapper.toResponse(null);

        // then
        assertNull(result);
    }

    @Test
    void toResponse_whenEntityIsValid_mapsFieldsCorrectly() {
        // given
        Category entity = new Category();
        entity.setId(1L);
        entity.setTitle("Electronics");

        // when
        CategoryResponse result = categoryMapper.toResponse(entity);

        // then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Electronics", result.getTitle());
    }
}
