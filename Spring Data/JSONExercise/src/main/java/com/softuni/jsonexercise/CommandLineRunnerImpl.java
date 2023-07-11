package com.softuni.jsonexercise;

import com.google.gson.Gson;
import com.softuni.jsonexercise.model.dto.CategoryInfoDto;
import com.softuni.jsonexercise.model.dto.ProductNameAndPriceDto;
import com.softuni.jsonexercise.model.dto.UserSoldDto;
import com.softuni.jsonexercise.model.entity.User;
import com.softuni.jsonexercise.service.CategoryService;
import com.softuni.jsonexercise.service.ProductService;
import com.softuni.jsonexercise.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private static final String OUTPUT_PATH = "src/main/resources/files/out/";
    private static final String PRODUCTS_IN_RANGE_FILE_NAME = "products-in-range.json";
    private static final String USERS_AND_SOLD_PRODUCTS = "users-and-sold-products";
    private final CategoryService categoryService;
    private final UserService userService;
    private final ProductService productService;
    private final Scanner sc;
    private final Gson gson;

    public CommandLineRunnerImpl(CategoryService categoryService, UserService userService, ProductService productService, Gson gson) {
        this.categoryService = categoryService;
        this.userService = userService;
        this.productService = productService;
        this.gson = gson;
        sc = new Scanner(System.in);
    }

    @Override
    public void run(String... args) throws Exception {
        seedData();

        System.out.println("Enter exercise:");
        int exerciseNumber = Integer.parseInt(sc.nextLine());

        switch (exerciseNumber) {
            case 1 -> productsInRange();
            case 2 -> soldProducts();
            case 3 -> categoriesByProductsCount();
        }
    }

    private void categoriesByProductsCount() {
        List<CategoryInfoDto> categoryInfoDtos = categoryService
                .findAllCategoriesInfo();
    }

    private void soldProducts() throws IOException {
        List<UserSoldDto> userSoldDtos = userService
                .findAllUsersWithAtLeastOneSoldProduct();

        String content = gson.toJson(userSoldDtos);

        writeToFile(OUTPUT_PATH + USERS_AND_SOLD_PRODUCTS, content);
    }

    private void productsInRange() throws IOException {
        List<ProductNameAndPriceDto> productsDTos = productService
                .findAllProductsInRangeOrderByPrice(BigDecimal.valueOf(500), BigDecimal.valueOf(1000));


        String content = gson.toJson(productsDTos);

        writeToFile(OUTPUT_PATH + PRODUCTS_IN_RANGE_FILE_NAME, content);
    }

    private void writeToFile(String filePath, String content) throws IOException {
        Files
                .write(Path.of(filePath), Collections.singleton(content));
    }

    private void seedData() throws IOException {
        categoryService.seedCategories();
        userService.seedUsers();
        productService.seedProducts();
    }
}
