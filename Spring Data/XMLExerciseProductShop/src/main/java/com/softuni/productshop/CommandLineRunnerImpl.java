package com.softuni.productshop;

import com.softuni.productshop.model.dto.*;
import com.softuni.productshop.model.dto.ex4.UserCountDto;
import com.softuni.productshop.service.CategoryService;
import com.softuni.productshop.service.ProductService;
import com.softuni.productshop.service.UserService;
import com.softuni.productshop.util.XmlParser;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.Scanner;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private static final String RESOURCES_FILE_PATH = "src/main/resources/files/";
    private static final String OUTPUT_FILE_PATH = "out/";
    private static final String CATEGORIES_FILE_NAME = "categories.xml";
    private static final String USERS_FILE_NAME = "users.xml";
    private static final String PRODUCTS_FILE_NAME = "products.xml";
    private static final String PRODUCTS_IN_RANGE_FILE_NAME = "products-in-range.xml";
    private static final String SOLD_PRODUCTS_FILE_NAME = "sold-products.xml";
    private static final String CATEGORIES_SUMMARY_FILE_NAME = "categories_summary.xml";
    private static final String USERS_AND_PRODUCTS = "users-and-products.xml";
    private final ProductService productService;
    private final XmlParser xmlParser;
    private final CategoryService categoryService;
    private final UserService userService;
    private final Scanner sc;

    public CommandLineRunnerImpl(ProductService productService, XmlParser xmlParser, CategoryService categoryService, UserService userService) {
        this.productService = productService;
        this.xmlParser = xmlParser;
        this.categoryService = categoryService;
        this.userService = userService;
        this.sc = new Scanner(System.in);
    }

    @Override
    public void run(String... args) throws Exception {
        seedData();

        System.out.println("Select exercise:");
        int exerciseNumber = Integer.parseInt(sc.nextLine());

        switch (exerciseNumber) {
            case 1 -> productsInRange();
            case 2 -> successfullySoldProducts();
            case 3 -> categoriesByProductsCount();
            case 4 -> usersAndProducts();
        }
    }

    private void usersAndProducts() throws JAXBException {
        UserCountDto userCountDto = userService
                .getAllUsersWithAtLeast1ProductSoldOrderByNumberOfProductsDesc();

        xmlParser.writeToFile(RESOURCES_FILE_PATH + OUTPUT_FILE_PATH + USERS_AND_PRODUCTS, userCountDto);
    }

    private void categoriesByProductsCount() throws JAXBException {
        CategoryViewRootDto categoryViewRootDto = categoryService
                .findAllCategoriesOrderByNumberOfProducts();

        xmlParser.writeToFile(RESOURCES_FILE_PATH + OUTPUT_FILE_PATH + CATEGORIES_SUMMARY_FILE_NAME,
                categoryViewRootDto);
    }

    private void successfullySoldProducts() throws JAXBException {
        UserViewRootDto userViewRootDto = userService
                .findUsersWithAtLeastOneSoldProduct();

        xmlParser.writeToFile(RESOURCES_FILE_PATH + OUTPUT_FILE_PATH + SOLD_PRODUCTS_FILE_NAME, userViewRootDto);
    }

    private void productsInRange() throws JAXBException {
        ProductViewRootDto rootDto = productService
                .findProductsInRangeWithNoBuyer();

        xmlParser.writeToFile(RESOURCES_FILE_PATH + OUTPUT_FILE_PATH + PRODUCTS_IN_RANGE_FILE_NAME,
                rootDto);
    }

    private void seedData() throws JAXBException, FileNotFoundException {

        if (categoryService.getEntityCount() == 0) {
            CategorySeedRootDto categorySeedRootDto = xmlParser.fromFile(RESOURCES_FILE_PATH + CATEGORIES_FILE_NAME,
                    CategorySeedRootDto.class);

            categoryService.seedCategories(categorySeedRootDto.getCategories());
        }

        if (userService.getCount() == 0) {
            UserSeedRootDto userSeedRootDto = xmlParser
                    .fromFile(RESOURCES_FILE_PATH + USERS_FILE_NAME,
                            UserSeedRootDto.class);

            userService.seedUsers(userSeedRootDto.getUsers());

        }

        if (productService.getCount() == 0) {
            ProductSeedRootDto productSeedRootDto = xmlParser
                    .fromFile(RESOURCES_FILE_PATH + PRODUCTS_FILE_NAME,
                            ProductSeedRootDto.class);

           productService.seedProducts(productSeedRootDto.getProducts());
        }
    }
}
