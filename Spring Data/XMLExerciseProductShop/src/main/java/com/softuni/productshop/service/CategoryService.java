package com.softuni.productshop.service;

import com.softuni.productshop.model.dto.CategorySeedDto;
import com.softuni.productshop.model.dto.CategoryViewRootDto;
import com.softuni.productshop.model.entity.Category;

import java.util.List;
import java.util.Set;

public interface CategoryService {
    void seedCategories(List<CategorySeedDto> categories);

    long getEntityCount();

    Set<Category> getRandomCategories();

    CategoryViewRootDto findAllCategoriesOrderByNumberOfProducts();
}
