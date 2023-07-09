package com.softuni.springautomapex.gamestore.service;

import com.softuni.springautomapex.gamestore.model.dto.UserLoginDto;
import com.softuni.springautomapex.gamestore.model.dto.UserRegisterDto;
import com.softuni.springautomapex.gamestore.model.entity.User;

public interface UserService {
    void register(UserRegisterDto userRegisterDto);

    void login(UserLoginDto userLoginDto);

    void logout();

    boolean isLoggedIn();

    User getCurrentUser();

    boolean isAdmin();
}
