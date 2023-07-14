package com.softuni.productshop.service;

import com.softuni.productshop.model.dto.CategoryInfoDto;
import com.softuni.productshop.model.entity.Category;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface CategoryService {
    void seedCategories() throws IOException;

    Set<Category> findRandomCategories();

    List<CategoryInfoDto> findAllCategoriesInfo();
}
