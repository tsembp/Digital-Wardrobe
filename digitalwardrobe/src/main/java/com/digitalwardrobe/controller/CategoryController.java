package com.digitalwardrobe.controller;

import com.digitalwardrobe.models.Category;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @GetMapping
    public List<Category> getCategories() {
        return Arrays.asList(Category.values());
    }
}