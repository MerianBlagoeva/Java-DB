package com.softuni.productshop.service;

import com.softuni.productshop.model.dto.UserSoldDto;
import com.softuni.productshop.model.dto.UserWithSoldProductWrapperDto;
import com.softuni.productshop.model.entity.User;

import java.io.IOException;
import java.util.List;

public interface UserService {
    void seedUsers() throws IOException;

    User findRandomUser();

    List<UserSoldDto> findAllUsersWithAtLeastOneSoldProduct();

    UserWithSoldProductWrapperDto findAllUsersWithSoldProductOrderByProductsSoldDesc();

}
