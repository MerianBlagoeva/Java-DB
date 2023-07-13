package com.softuni.jsonexercise.service;

import com.softuni.jsonexercise.model.dto.UserSoldDto;
import com.softuni.jsonexercise.model.dto.UserWithSoldProductWrapperDto;
import com.softuni.jsonexercise.model.entity.User;

import java.io.IOException;
import java.util.List;

public interface UserService {
    void seedUsers() throws IOException;

    User findRandomUser();

    List<UserSoldDto> findAllUsersWithAtLeastOneSoldProduct();

    UserWithSoldProductWrapperDto findAllUsersWithSoldProductOrderByProductsSoldDesc();

}
