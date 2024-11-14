package com.saket.dreamshop.repository;

import com.saket.dreamshop.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, String>
{

    Category findByName(String name);
}
