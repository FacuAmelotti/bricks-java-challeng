package com.bricks.challenge.service;

import com.bricks.challenge.dto.category.CategoryResponse;
import com.bricks.challenge.entity.Category;
import com.bricks.challenge.exception.ResourceNotFoundException;
import com.bricks.challenge.mapper.CategoryMapper;
import com.bricks.challenge.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private Category category1;
    private Category category2;
    private CategoryResponse response1;
    private CategoryResponse response2;

    @BeforeEach
    void setUp() {
        category1 = new Category();
        category1.setId(1L);
        category1.setTitle("Electronics");

        category2 = new Category();
        category2.setId(2L);
        category2.setTitle("Home");

        response1 = new CategoryResponse();
        response1.setId(1L);
        response1.setTitle("Electronics");

        response2 = new CategoryResponse();
        response2.setId(2L);
        response2.setTitle("Home");
    }

    @Test
    @DisplayName("findAll() debe devolver todas las categorías mapeadas")
    void findAll_returnsAllCategories() {
        when(categoryRepository.findAll()).thenReturn(Arrays.asList(category1, category2));
        when(categoryMapper.toResponse(category1)).thenReturn(response1);
        when(categoryMapper.toResponse(category2)).thenReturn(response2);

        List<CategoryResponse> result = categoryService.findAll();

        assertThat(result).hasSize(2);
        assertThat(result)
                .extracting(CategoryResponse::getId)
                .containsExactly(1L, 2L);

        verify(categoryRepository, times(1)).findAll();
        verify(categoryMapper, times(1)).toResponse(category1);
        verify(categoryMapper, times(1)).toResponse(category2);
    }

    @Test
    @DisplayName("findById() debe devolver CategoryResponse cuando la categoría existe")
    void findById_whenExists_returnsResponse() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category1));
        when(categoryMapper.toResponse(category1)).thenReturn(response1);

        CategoryResponse result = categoryService.findById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);

        verify(categoryRepository, times(1)).findById(1L);
        verify(categoryMapper, times(1)).toResponse(category1);
    }

    @Test
    @DisplayName("findById() debe lanzar ResourceNotFoundException cuando la categoría no existe")
    void findById_whenNotFound_throwsResourceNotFoundException() {
        when(categoryRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoryService.findById(99L))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(categoryRepository, times(1)).findById(99L);
    }

    @Test
    @DisplayName("getEntityById() debe devolver la entidad cuando existe")
    void getEntityById_whenExists_returnsEntity() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category1));

        Category result = categoryService.getEntityById(1L);

        assertThat(result).isEqualTo(category1);
        verify(categoryRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("getEntityById() debe lanzar ResourceNotFoundException cuando no existe")
    void getEntityById_whenNotFound_throwsResourceNotFoundException() {
        when(categoryRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoryService.getEntityById(99L))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(categoryRepository, times(1)).findById(99L);
    }
}
