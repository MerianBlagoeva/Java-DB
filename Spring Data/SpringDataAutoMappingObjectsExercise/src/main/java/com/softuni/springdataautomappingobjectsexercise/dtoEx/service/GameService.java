package com.softuni.springdataautomappingobjectsexercise.dtoEx.service;

import com.softuni.springdataautomappingobjectsexercise.dtoEx.model.dto.GameAddDto;

import java.math.BigDecimal;

public interface GameService {
    void addGame(GameAddDto gameAddDto);

//    void editGame(Long gameId, String[] editInfo);
//
//    void deleteGame(long gameId);

    void printAllGamesInfo();

    void printGameInfo(String gameTitle);
}
