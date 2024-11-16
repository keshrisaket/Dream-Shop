package com.saket.dreamshop.controller;


import com.saket.dreamshop.service.category.CategoryService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/category")
public class CategoryController {
    private final CategoryService categoryService;



    CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }




}
