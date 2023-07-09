package com.softuni.springautomapex.gamestore.service;

import com.softuni.springautomapex.gamestore.model.dto.GameAddDto;
import com.softuni.springautomapex.gamestore.model.entity.Game;
import com.softuni.springautomapex.gamestore.model.entity.User;

public interface GameService {
    void addGame(GameAddDto gameAddDto);

    void editGame(Long gameId, String[] editInfo);
    void deleteGame(long gameId);

    void printUserGames(User user);

    Game getGame(long id);
    void printAllGamesInfo();

    void printGameDetails(String gameTitle);
}
