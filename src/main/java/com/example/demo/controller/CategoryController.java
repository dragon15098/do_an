package com.example.demo.controller;

import com.example.demo.model.Category;
import com.example.demo.model.dto.CategoryDTO;
import com.example.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("/get_all")
    public ResponseEntity<List<CategoryDTO>> getAllCategory() {
        return new ResponseEntity<>(categoryService.getAllCategory(), HttpStatus.OK);
    }

    @PostMapping("")
    public  ResponseEntity<CategoryDTO> insertOrUpdate(@RequestBody CategoryDTO category) {
        return new ResponseEntity<>(categoryService.insertOrUpdate(category), HttpStatus.OK);
    }
}
