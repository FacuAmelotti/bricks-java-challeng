package com.bricks.challenge.controller;

import com.bricks.challenge.dto.product.ProductRequest;
import com.bricks.challenge.dto.product.ProductResponse;
import com.bricks.challenge.exception.GlobalExceptionHandler;
import com.bricks.challenge.exception.ResourceNotFoundException;
import com.bricks.challenge.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProductControllerTest {

    private MockMvc mockMvc;
    private ProductService productService;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        productService = Mockito.mock(ProductService.class);
        objectMapper = new ObjectMapper();

        ProductController controller = new ProductController(productService);
        GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(exceptionHandler)
                .build();
    }

    private ProductResponse buildResponse(Long id, String name, double price, int stock) {
        ProductResponse r = new ProductResponse();
        r.setId(id);
        r.setName(name);
        r.setPrice(BigDecimal.valueOf(price));
        r.setStock(stock);
        return r;
    }

    @Test
    @DisplayName("GET /api/products debe devolver 200 y listado JSON")
    void getAllProducts_returnsOkAndList() throws Exception {
        ProductResponse p1 = buildResponse(1L, "Prod 1", 10.5, 5);
        ProductResponse p2 = buildResponse(2L, "Prod 2", 20.0, 3);

        when(productService.findAll(any())).thenReturn(List.of(p1, p2));

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Prod 1")));
    }

    @Test
    @DisplayName("GET /api/products/{id} debe devolver 200 cuando existe")
    void getProductById_whenExists_returnsOk() throws Exception {
        ProductResponse p1 = buildResponse(1L, "Prod 1", 10.5, 5);
        when(productService.findById(1L)).thenReturn(p1);

        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Prod 1")));
    }

    @Test
    @DisplayName("GET /api/products/{id} debe devolver 404 cuando no existe")
    void getProductById_whenNotFound_returns404() throws Exception {
        when(productService.findById(99L))
                .thenThrow(new ResourceNotFoundException("Product not found"));

        mockMvc.perform(get("/api/products/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /api/products debe devolver 201 cuando el body es válido")
    void createProduct_returnsCreated() throws Exception {
        ProductRequest req = new ProductRequest();
        req.setName("New Product");
        req.setPrice(BigDecimal.valueOf(100.0));
        req.setStock(10);
        req.setCategoryId(1L);

        ProductResponse resp = buildResponse(10L, "New Product", 100.0, 10);
        when(productService.create(any(ProductRequest.class))).thenReturn(resp);

        mockMvc.perform(
                        post("/api/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(req))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(10)))
                .andExpect(jsonPath("$.name", is("New Product")));
    }

    @Test
    @DisplayName("PUT /api/products/{id} debe devolver 200 cuando el producto existe")
    void updateProduct_whenExists_returnsOk() throws Exception {
        ProductRequest req = new ProductRequest();
        req.setName("Updated");
        req.setPrice(BigDecimal.valueOf(200.0));
        req.setStock(5);
        req.setCategoryId(1L);

        ProductResponse resp = buildResponse(1L, "Updated", 200.0, 5);
        when(productService.update(eq(1L), any(ProductRequest.class))).thenReturn(resp);

        mockMvc.perform(
                        put("/api/products/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(req))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Updated")));
    }

    @Test
    @DisplayName("PUT /api/products/{id} debe devolver 404 cuando el producto no existe")
    void updateProduct_whenNotFound_returns404() throws Exception {
        ProductRequest req = new ProductRequest();
        req.setName("Updated");
        req.setPrice(BigDecimal.valueOf(200.0));
        req.setStock(5);
        req.setCategoryId(1L);

        when(productService.update(eq(99L), any(ProductRequest.class)))
                .thenThrow(new ResourceNotFoundException("Not found"));

        mockMvc.perform(
                        put("/api/products/99")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(req))
                )
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /api/products/{id} debe devolver 204 cuando elimina correctamente")
    void deleteProduct_whenExists_returns204() throws Exception {
        mockMvc.perform(delete("/api/products/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /api/products/{id} debe devolver 404 cuando no existe")
    void deleteProduct_whenNotFound_returns404() throws Exception {
        doThrow(new ResourceNotFoundException("Not found"))
                .when(productService).delete(99L);

        mockMvc.perform(delete("/api/products/99"))
                .andExpect(status().isNotFound());
    }
}
