package com.bricks.challenge.service;

import com.bricks.challenge.dto.product.ProductFilter;
import com.bricks.challenge.dto.product.ProductRequest;
import com.bricks.challenge.dto.product.ProductResponse;

import java.util.List;

public interface ProductService {

    ProductResponse create(ProductRequest request);

    ProductResponse findById(Long id);

    List<ProductResponse> findAll(ProductFilter filter);

    ProductResponse update(Long id, ProductRequest request);

    void delete(Long id);
}
