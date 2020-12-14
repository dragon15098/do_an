package com.example.demo.service.impl;

import com.example.demo.model.Category;
import com.example.demo.model.dto.CategoryDTO;
import com.example.demo.model.helper.CategoryHelper;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    public final CategoryRepository categoryRepository;

    @Override
    public List<CategoryDTO> getAllCategory() {
        return categoryRepository.getAllCategory().stream().map(tuple -> {
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setId((Long) tuple.get("id"));
            categoryDTO.setName((String) tuple.get("name"));
            return categoryDTO;
        }).collect(Collectors.toList());

    }

    @Override
    public CategoryDTO insertOrUpdate(CategoryDTO categoryDTO) {
        CategoryHelper categoryHelper = new CategoryHelper(categoryDTO);
        Category category = categoryHelper.categoryDTOToCategory();
        category = categoryRepository.save(category);
        categoryDTO.setId(category.getId());
        return categoryDTO;
    }

    @Override
    public CategoryDTO getCategoryById(Long categoryId) {
        return categoryRepository.getCategoryById(categoryId).stream().map(tuple -> {
            CategoryDTO category = new CategoryDTO();
            category.setId((Long) tuple.get("id"));
            category.setName((String) tuple.get("name"));
            return category;
        }).findFirst().orElse(new CategoryDTO());
    }
}
