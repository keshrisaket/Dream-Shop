package com.saket.dreamshop.service.category;

import com.saket.dreamshop.entity.Category;
import com.saket.dreamshop.exception.AlreadyExistsException;
import com.saket.dreamshop.exception.ResourceNotFoundException;
import com.saket.dreamshop.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CategoryService implements ICategoryService
{
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Override
    public Category getCategoryById(String id) {
        return categoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category not found! "));
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public Category addCategory(Category category) {
        return Optional.of(category).filter(c -> !categoryRepository.existsByName(c.getName()))
                .map(categoryRepository :: save)
                .orElseThrow(()-> new AlreadyExistsException(category.getName()+" already exists"));
    }



    @Override
    public Category updateCategory(Category category, String id) {

        return Optional.ofNullable(getCategoryById(id)).map(oldCategory -> {
            oldCategory.setName(category.getName());
            return categoryRepository.save(oldCategory);
        }).orElseThrow(()-> new ResourceNotFoundException("Category not found!"));
    }


    @Override
    public void deleteCategory(String id) {

        categoryRepository.findById(id).ifPresentOrElse(categoryRepository :: delete, () -> {
            throw new ResourceNotFoundException("Category not found!");
        } );

    }
}
