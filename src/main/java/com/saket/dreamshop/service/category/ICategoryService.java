package com.saket.dreamshop.service.category;

import com.saket.dreamshop.entity.Category;

import java.util.List;

public interface ICategoryService {
    Category getCategoryById(String id);

    List<Category> getAllCategories();

    Category getCategoryByName(String name);

    Category addCategory(Category category);

    Category updateCategory(Category category, String id);

    void deleteCategory(String id);


}

