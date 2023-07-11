package com.softuni.jsonexercise.service.impl;

import com.google.gson.Gson;
import com.softuni.jsonexercise.constants.GlobalConstants;
import com.softuni.jsonexercise.model.dto.CategoryInfoDto;
import com.softuni.jsonexercise.model.dto.CategorySeedDto;
import com.softuni.jsonexercise.model.dto.ProductNameAndPriceDto;
import com.softuni.jsonexercise.model.entity.Category;
import com.softuni.jsonexercise.repository.CategoryRepository;
import com.softuni.jsonexercise.service.CategoryService;
import com.softuni.jsonexercise.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private static final String CATEGORIES_FILE_NAME = "categories.json";

    private final CategoryRepository categoryRepository;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }


    @Override
    public void seedCategories() throws IOException {

        if (categoryRepository.count() > 0) {
            return;
        }


        String fileContent = Files
                .readString(Path.of(GlobalConstants.RESOURCES_FILE_PATH + CATEGORIES_FILE_NAME));

        CategorySeedDto[] categorySeedDtos = gson
                .fromJson(fileContent, CategorySeedDto[].class);

        Arrays.stream(categorySeedDtos)
                .filter(validationUtil::isValid)
                .map(categorySeedDto -> modelMapper.map(categorySeedDto, Category.class))
                .forEach(categoryRepository::save);

    }

    @Override
    public Set<Category> findRandomCategories() {
        Set<Category> categorySet = new HashSet<>();
        int categoryCount = ThreadLocalRandom.current().nextInt(1, 3);
        long totalCategoriesCount = categoryRepository.count();

        for (int i = 0; i < categoryCount; i++) {
            Long randomId = ThreadLocalRandom
                    .current().nextLong(1, totalCategoriesCount + 1);

            categorySet.add(categoryRepository.findById(randomId).orElse(null));
        }

        return categorySet;
    }

    @Override
    public List<CategoryInfoDto> findAllCategoriesInfo() {
        return categoryRepository.findAllOrderByNumberOfProducts()
                .stream()
                .map(category -> {
                    CategoryInfoDto categoryInfoDto = modelMapper
                            .map(category, CategoryInfoDto.class);

                    categoryInfoDto.setProductsCount(2);

                    return categoryInfoDto;

                })
                .collect(Collectors.toList());

    }
}
