package com.softuni.productshop.service.impl;

import com.softuni.productshop.model.dto.CategorySeedDto;
import com.softuni.productshop.model.dto.CategorySummaryDto;
import com.softuni.productshop.model.dto.CategoryViewRootDto;
import com.softuni.productshop.model.entity.Category;
import com.softuni.productshop.model.entity.Product;
import com.softuni.productshop.repository.CategoryRepository;
import com.softuni.productshop.service.CategoryService;
import com.softuni.productshop.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public void seedCategories(List<CategorySeedDto> categories) {
        categories
                .stream()
                .filter(validationUtil::isValid)
                .map(categorySeedDto -> modelMapper.map(categorySeedDto, Category.class));
    }

    @Override
    public long getEntityCount() {
        return categoryRepository.count();
    }

    @Override
    public Set<Category> getRandomCategories() {
        Set<Category> categories = new HashSet<>();
        long categoriesCount = categoryRepository.count();

        for (int i = 0; i < 2; i++) {
            long randomId = ThreadLocalRandom
                    .current().nextLong(1, categoriesCount + 1);

            categories.add(categoryRepository
                    .findById(randomId)
                    .orElse(null));
        }

        return categories;
    }

    @Override
    public CategoryViewRootDto findAllCategoriesOrderByNumberOfProducts() {

        CategoryViewRootDto categoryViewRootDto =  new CategoryViewRootDto();

        categoryViewRootDto.setCategories(
        categoryRepository.findAllOrderByProductsCount()
                .stream()
                .map(category -> {
                    CategorySummaryDto categorySummaryDto = modelMapper.map(category, CategorySummaryDto.class);

                    categorySummaryDto.setProductsCount(category.getProducts().size());

                    categorySummaryDto.setAveragePrice(BigDecimal.valueOf(category.getProducts()
                            .stream()
                            .mapToDouble(p -> p.getPrice().doubleValue())
                            .average().orElse(0.00)));

                    categorySummaryDto.setTotalRevenue(BigDecimal.valueOf(category.getProducts()
                            .stream()
                            .mapToDouble(p -> p.getPrice().doubleValue())
                            .sum()));

                    return categorySummaryDto;
                })
                .collect(Collectors.toList()));

        return categoryViewRootDto;
    }
}
