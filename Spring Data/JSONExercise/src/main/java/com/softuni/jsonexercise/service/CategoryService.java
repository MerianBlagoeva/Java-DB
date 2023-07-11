package com.softuni.jsonexercise.service;

import com.softuni.jsonexercise.model.dto.CategoryInfoDto;
import com.softuni.jsonexercise.model.entity.Category;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface CategoryService {
    void seedCategories() throws IOException;

    Set<Category> findRandomCategories();

    List<CategoryInfoDto> findAllCategoriesInfo();
}
