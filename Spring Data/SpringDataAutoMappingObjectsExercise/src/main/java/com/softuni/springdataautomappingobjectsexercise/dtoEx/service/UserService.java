package com.softuni.springdataautomappingobjectsexercise.dtoEx.service;

import com.softuni.springdataautomappingobjectsexercise.dtoEx.model.dto.UserLoginDto;
import com.softuni.springdataautomappingobjectsexercise.dtoEx.model.dto.UserRegisterDto;

public interface UserService {
    void registerUser(UserRegisterDto userRegisterDto);

    void loginUser(UserLoginDto userLoginDto);

    void logout();

    void deleteGame(long gameId);

    void editGame(Long gameId, String[] editInfo);

    void printOwnedGames();
}
