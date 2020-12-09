package com.example.demo.model.helper;

import com.example.demo.model.Category;
import com.example.demo.model.dto.CategoryDTO;

public class CategoryHelper {
    private final CategoryDTO categoryDTO;

    public CategoryHelper(CategoryDTO categoryDTO) {
        this.categoryDTO = categoryDTO;
    }

    public Category categoryDTOToCategory(){
        Category category = new Category();
        category.setName(this.categoryDTO.getName());
        category.setId(this.categoryDTO.getId());
        return category;
    }

}
