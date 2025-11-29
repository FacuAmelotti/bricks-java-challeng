package com.bricks.challenge.dto.product;

import java.math.BigDecimal;

public class ProductFilter {

    // Filtros existentes
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Long categoryId;

    // NUEVOS filtros según enunciado
    private String name;        // filtro por nombre (contiene)
    private Integer minStock;   // stock mínimo
    private Integer maxStock;   // stock máximo

    // Paginación
    private Integer page;       // número de página (0-based)
    private Integer size;       // tamaño de página

    // Getters y Setters

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMinStock() {
        return minStock;
    }

    public void setMinStock(Integer minStock) {
        this.minStock = minStock;
    }

    public Integer getMaxStock() {
        return maxStock;
    }

    public void setMaxStock(Integer maxStock) {
        this.maxStock = maxStock;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
