package com.bricks.challenge.service;

import com.bricks.challenge.dto.category.CategoryResponse;
import com.bricks.challenge.entity.Category;

import java.util.List;

public interface CategoryService {

    List<CategoryResponse> findAll();

    CategoryResponse findById(Long id);

    /**
     * Devuelve la entidad Category para uso interno en otros servicios.
     */
    Category getEntityById(Long id);
}
