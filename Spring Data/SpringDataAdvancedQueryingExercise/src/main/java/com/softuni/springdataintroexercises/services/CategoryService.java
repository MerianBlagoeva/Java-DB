package com.softuni.springdataintroexercises.services;

import com.softuni.springdataintroexercises.models.Category;

import java.io.IOException;
import java.util.Set;

public interface CategoryService {
    void seedCategories() throws IOException;

    Set<Category> getRandomCategories();
}
