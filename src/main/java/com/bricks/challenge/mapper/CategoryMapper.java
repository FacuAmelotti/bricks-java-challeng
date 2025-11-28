package com.bricks.challenge.mapper;

import com.bricks.challenge.dto.category.CategoryResponse;
import com.bricks.challenge.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryResponse toResponse(Category entity) {
        if (entity == null) {
            return null;
        }

        CategoryResponse dto = new CategoryResponse();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        return dto;
    }

    public Category toEntity(CategoryResponse dto) {
        if (dto == null) {
            return null;
        }

        Category entity = new Category();
        entity.setId(dto.getId());
        entity.setTitle(dto.getTitle());
        return entity;
    }
}
