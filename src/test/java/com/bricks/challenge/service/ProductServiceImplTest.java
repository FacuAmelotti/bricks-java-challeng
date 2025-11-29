package com.bricks.challenge.service;

import com.bricks.challenge.dto.product.ProductFilter;
import com.bricks.challenge.dto.product.ProductRequest;
import com.bricks.challenge.dto.product.ProductResponse;
import com.bricks.challenge.entity.Category;
import com.bricks.challenge.entity.Product;
import com.bricks.challenge.exception.ResourceNotFoundException;
import com.bricks.challenge.mapper.ProductMapper;
import com.bricks.challenge.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryService categoryService;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    private Category category;
    private Product product1;
    private Product product2;
    private ProductRequest productRequest;
    private ProductResponse productResponse1;
    private ProductResponse productResponse2;

    @BeforeEach
    void setUp() {
        category = new Category();
        category.setId(1L);
        category.setTitle("Electronics");

        product1 = new Product();
        product1.setId(1L);
        product1.setName("Laptop");
        product1.setPrice(new BigDecimal("1000.00"));
        product1.setStock(5);
        product1.setCategory(category);

        product2 = new Product();
        product2.setId(2L);
        product2.setName("Mouse");
        product2.setPrice(new BigDecimal("50.00"));
        product2.setStock(20);
        product2.setCategory(category);

        productRequest = new ProductRequest();
        productRequest.setName("New Laptop");
        productRequest.setPrice(new BigDecimal("1234.56"));
        productRequest.setStock(10);
        productRequest.setCategoryId(1L);

        productResponse1 = new ProductResponse();
        productResponse1.setId(1L);
        productResponse1.setName("Laptop");
        productResponse1.setPrice(product1.getPrice());
        productResponse1.setStock(product1.getStock());

        productResponse2 = new ProductResponse();
        productResponse2.setId(2L);
        productResponse2.setName("Mouse");
        productResponse2.setPrice(product2.getPrice());
        productResponse2.setStock(product2.getStock());
    }

    @Test
    @DisplayName("findAll(null) debe devolver todos los productos mapeados")
    void findAllWithoutFilter_returnsAllProducts() {
        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));
        when(productMapper.toResponse(product1)).thenReturn(productResponse1);
        when(productMapper.toResponse(product2)).thenReturn(productResponse2);

        List<ProductResponse> result = productService.findAll(null);

        assertThat(result).hasSize(2);
        assertThat(result)
                .extracting(ProductResponse::getId)
                .containsExactly(1L, 2L);

        verify(productRepository, times(1)).findAll();
        verify(productMapper, times(1)).toResponse(product1);
        verify(productMapper, times(1)).toResponse(product2);
    }

    @Test
    @DisplayName("findAll(filter) debe funcionar con filtro no nulo y devolver productos mapeados")
    void findAllWithFilter_returnsProducts() {
        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));
        when(productMapper.toResponse(product1)).thenReturn(productResponse1);
        when(productMapper.toResponse(product2)).thenReturn(productResponse2);

        ProductFilter filter = new ProductFilter();

        List<ProductResponse> result = productService.findAll(filter);

        assertThat(result).hasSize(2);
        verify(productRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("findById() debe devolver ProductResponse cuando el producto existe")
    void findById_whenExists_returnsProductResponse() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product1));
        when(productMapper.toResponse(product1)).thenReturn(productResponse1);

        ProductResponse result = productService.findById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);

        verify(productRepository, times(1)).findById(1L);
        verify(productMapper, times(1)).toResponse(product1);
    }

    @Test
    @DisplayName("findById() debe lanzar ResourceNotFoundException cuando el producto no existe")
    void findById_whenNotFound_throwsResourceNotFoundException() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.findById(99L))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(productRepository, times(1)).findById(99L);
        verifyNoMoreInteractions(productRepository);
    }

    @Test
    @DisplayName("create() debe resolver la categoría, mapear, guardar y devolver ProductResponse")
    void create_withExistingCategory_savesAndReturnsResponse() {
        when(categoryService.getEntityById(1L)).thenReturn(category);

        Product productToSave = new Product();
        when(productMapper.toEntity(productRequest, category)).thenReturn(productToSave);

        Product savedProduct = new Product();
        savedProduct.setId(10L);
        savedProduct.setName(productRequest.getName());
        savedProduct.setPrice(productRequest.getPrice());
        savedProduct.setStock(productRequest.getStock());
        savedProduct.setCategory(category);

        when(productRepository.save(productToSave)).thenReturn(savedProduct);

        ProductResponse savedResponse = new ProductResponse();
        savedResponse.setId(10L);
        savedResponse.setName(productRequest.getName());
        savedResponse.setPrice(productRequest.getPrice());
        savedResponse.setStock(productRequest.getStock());
        when(productMapper.toResponse(savedProduct)).thenReturn(savedResponse);

        ProductResponse result = productService.create(productRequest);

        assertThat(result.getId()).isEqualTo(10L);
        assertThat(result.getName()).isEqualTo(productRequest.getName());

        verify(categoryService, times(1)).getEntityById(1L);
        verify(productMapper, times(1)).toEntity(productRequest, category);
        verify(productRepository, times(1)).save(productToSave);
        verify(productMapper, times(1)).toResponse(savedProduct);
    }

    @Test
    @DisplayName("update() debe actualizar el producto existente y devolver ProductResponse")
    void update_whenExists_updatesAndReturnsResponse() {
        Long id = 1L;

        Product existingProduct = new Product();
        existingProduct.setId(id);
        existingProduct.setName("Old Name");
        existingProduct.setPrice(new BigDecimal("999.99"));
        existingProduct.setStock(1);
        existingProduct.setCategory(category);

        when(productRepository.findById(id)).thenReturn(Optional.of(existingProduct));
        when(categoryService.getEntityById(1L)).thenReturn(category);

        Product savedProduct = new Product();
        savedProduct.setId(id);
        savedProduct.setName(productRequest.getName());
        savedProduct.setPrice(productRequest.getPrice());
        savedProduct.setStock(productRequest.getStock());
        savedProduct.setCategory(category);

        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        ProductResponse updatedResponse = new ProductResponse();
        updatedResponse.setId(id);
        updatedResponse.setName(productRequest.getName());
        updatedResponse.setPrice(productRequest.getPrice());
        updatedResponse.setStock(productRequest.getStock());
        when(productMapper.toResponse(savedProduct)).thenReturn(updatedResponse);

        ProductResponse result = productService.update(id, productRequest);

        assertThat(result.getId()).isEqualTo(id);
        assertThat(result.getName()).isEqualTo(productRequest.getName());

        verify(productRepository, times(1)).findById(id);
        verify(categoryService, times(1)).getEntityById(1L);
        verify(productRepository, times(1)).save(any(Product.class));
        verify(productMapper, times(1)).toResponse(savedProduct);
    }

    @Test
    @DisplayName("delete() debe eliminar el producto cuando existe")
    void delete_whenExists_deletes() {
        Long id = 1L;
        when(productRepository.findById(id)).thenReturn(Optional.of(product1));

        productService.delete(id);

        verify(productRepository, times(1)).findById(id);
        verify(productRepository, times(1)).delete(product1);
    }

    @Test
    @DisplayName("delete() debe lanzar ResourceNotFoundException cuando el producto no existe")
    void delete_whenNotFound_throwsResourceNotFoundException() {
        Long id = 99L;
        when(productRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.delete(id))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(productRepository, times(1)).findById(id);
        verify(productRepository, never()).delete(any());
    }

    
}
