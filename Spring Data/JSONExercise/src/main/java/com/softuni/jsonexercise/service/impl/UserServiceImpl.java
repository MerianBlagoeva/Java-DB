package com.softuni.jsonexercise.service.impl;

import com.google.gson.Gson;
import com.softuni.jsonexercise.model.dto.*;
import com.softuni.jsonexercise.model.entity.Product;
import com.softuni.jsonexercise.model.entity.User;
import com.softuni.jsonexercise.repository.UserRepository;
import com.softuni.jsonexercise.service.UserService;
import com.softuni.jsonexercise.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static com.softuni.jsonexercise.constants.GlobalConstants.RESOURCES_FILE_PATH;

@Service
public class UserServiceImpl implements UserService {

    private static final String USERS_FILE_NAME = "users.json";
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public void seedUsers() throws IOException {

        if (userRepository.count() == 0) {
            Arrays.stream(gson.fromJson(
                            Files.readString(Path.of(RESOURCES_FILE_PATH + USERS_FILE_NAME)),
                            UserSeedDto[].class))
                    .filter(validationUtil::isValid)
                    .map(userSeedDto -> modelMapper.map(userSeedDto, User.class))
                    .forEach(userRepository::save);
        }
    }

    @Override
    public User findRandomUser() {
        long randomId = ThreadLocalRandom
                .current().nextLong(1, userRepository.count() + 1);

        return userRepository.findById(randomId).orElse(null);
    }

    @Override
    public List<UserSoldDto> findAllUsersWithAtLeastOneSoldProduct() {
        return userRepository
                .findAllUsersWithAtLeastOneSoldProductOrderByLastNameThenByFirstName()
                .stream()
                .map(user -> modelMapper.map(user, UserSoldDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserWithSoldProductWrapperDto findAllUsersWithSoldProductOrderByProductsSoldDesc() {
        List<User> users = userRepository.
                findAllUsersWithAtLeastOneSoldProductOrderBySoldProductsDescThenByLastNameAsc();


        List<UserWithSoldProductsDto> userWithSoldProductsDtos = new ArrayList<>();

        UserWithSoldProductWrapperDto userWithSoldProductWrapperDto = new UserWithSoldProductWrapperDto();

        for (User user : users) {
            Set<Product> soldProducts = user.getSoldProducts();

            List<ProductInfoDto> products = new ArrayList<>();

            for (Product product : soldProducts) {
                ProductInfoDto productInfoDto = modelMapper.map(product, ProductInfoDto.class);
                products.add(productInfoDto);
            }


            SoldProductsDto soldProductsDto = new SoldProductsDto();

            soldProductsDto.setProducts(products);
            soldProductsDto.setCount(products.size());


            UserWithSoldProductsDto userWithSoldProductsDto = modelMapper.map(user, UserWithSoldProductsDto.class);
            userWithSoldProductsDto.setSoldProducts(soldProductsDto);


            userWithSoldProductsDtos.add(userWithSoldProductsDto);
        }


        userWithSoldProductWrapperDto.setUsersCount(users.size());

        userWithSoldProductWrapperDto.setUsers(userWithSoldProductsDtos);

        return userWithSoldProductWrapperDto;


    }
}
