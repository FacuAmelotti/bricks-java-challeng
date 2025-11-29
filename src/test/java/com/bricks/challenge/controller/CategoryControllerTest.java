package com.bricks.challenge.controller;

import com.bricks.challenge.dto.category.CategoryResponse;
import com.bricks.challenge.exception.GlobalExceptionHandler;
import com.bricks.challenge.exception.ResourceNotFoundException;
import com.bricks.challenge.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests de CategoryController usando MockMvc en modo standalone.
 *
 * Endpoints:
 *  - GET /category
 *  - GET /category/{id}
 */
@ExtendWith(SpringExtension.class)
class CategoryControllerTest {

    private MockMvc mockMvc;
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        // Mock del service
        this.categoryService = Mockito.mock(CategoryService.class);

        // Instancia real del controller con el mock
        CategoryController categoryController = new CategoryController(categoryService);

        // Configuramos MockMvc en modo standalone + GlobalExceptionHandler
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(categoryController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    // -------------------------------------------------------------------------
    // GET /category - lista de categorías
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("GET /category debe devolver 200 y la lista de categorías")
    void findAll_returnsListOfCategories() throws Exception {
        CategoryResponse c1 = new CategoryResponse();
        c1.setId(1L);
        c1.setTitle("Electronics");

        CategoryResponse c2 = new CategoryResponse();
        c2.setId(2L);
        c2.setTitle("Books");

        when(categoryService.findAll()).thenReturn(List.of(c1, c2));

        mockMvc.perform(get("/category"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                // tamaño del array
                .andExpect(jsonPath("$", hasSize(2)))
                // primer elemento
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].title").value("Electronics"))
                // segundo elemento
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].title").value("Books"));
    }

    // -------------------------------------------------------------------------
    // GET /category/{id} - éxito
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("GET /category/{id} debe devolver 200 y la categoría cuando existe")
    void findById_whenExists_returnsCategory() throws Exception {
        CategoryResponse c1 = new CategoryResponse();
        c1.setId(1L);
        c1.setTitle("Electronics");

        when(categoryService.findById(1L)).thenReturn(c1);

        mockMvc.perform(get("/category/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Electronics"));
    }

    // -------------------------------------------------------------------------
    // GET /category/{id} - not found
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("GET /category/{id} debe devolver 404 cuando la categoría no existe")
    void findById_whenNotFound_returns404() throws Exception {
        when(categoryService.findById(anyLong()))
                .thenThrow(new ResourceNotFoundException("Category not found"));

        mockMvc.perform(get("/category/{id}", 99L))
                .andExpect(status().isNotFound());
                // Si tu GlobalExceptionHandler devuelve JSON, podríamos
                // matchear también el body, pero con el 404 ya está OK
    }
}
