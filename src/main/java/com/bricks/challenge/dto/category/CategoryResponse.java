package com.bricks.challenge.dto.category;

public class CategoryResponse {

    private Long id;
    private String title;

    public CategoryResponse() {
    }

    public CategoryResponse(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
