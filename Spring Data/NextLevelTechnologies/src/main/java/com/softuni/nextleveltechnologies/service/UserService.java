package com.softuni.nextleveltechnologies.service;

import com.softuni.nextleveltechnologies.dto.UserLoginDto;
import com.softuni.nextleveltechnologies.dto.UserRegisterDto;

public interface UserService {
    boolean register(UserRegisterDto user);

    Long validateUserLoginDetails(UserLoginDto userLoginDto);
}
