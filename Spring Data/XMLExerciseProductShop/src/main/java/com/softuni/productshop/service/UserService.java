package com.softuni.productshop.service;

import com.softuni.productshop.model.dto.UserSeedDto;
import com.softuni.productshop.model.dto.UserViewRootDto;
import com.softuni.productshop.model.dto.ex4.UserCountDto;
import com.softuni.productshop.model.entity.User;

import java.util.List;

public interface UserService {
    long getCount();

    void seedUsers(List<UserSeedDto> users);

    User getRandomUser();

    UserViewRootDto findUsersWithAtLeastOneSoldProduct();

    UserCountDto getAllUsersWithAtLeast1ProductSoldOrderByNumberOfProductsDesc();
}
