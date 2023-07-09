package com.softuni.springautomapex.gamestore.service.impl;

import com.softuni.springautomapex.gamestore.model.dto.GameAddDto;
import com.softuni.springautomapex.gamestore.model.entity.Game;
import com.softuni.springautomapex.gamestore.model.entity.User;
import com.softuni.springautomapex.gamestore.repository.GameRepository;
import com.softuni.springautomapex.gamestore.service.GameService;
import com.softuni.springautomapex.gamestore.util.ValidationUtil;
import jakarta.validation.ConstraintViolation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@Service
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;



    public GameServiceImpl(GameRepository gameRepository, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.gameRepository = gameRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public void addGame(GameAddDto gameAddDto) {
        Set<ConstraintViolation<GameAddDto>> violations = validationUtil.getViolations(gameAddDto);

        if (!violations.isEmpty()) {
            violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
            return;
        }

        Game game = modelMapper.map(gameAddDto, Game.class);
        game.setReleaseDate(LocalDate.parse(gameAddDto.getReleaseDate(), DateTimeFormatter.ofPattern("dd-MM-yyyy")));


        gameRepository.save(game);
        System.out.println("Added " + gameAddDto.getTitle());
    }

    public void editGame(Long gameId, String[] editInfo) {
        Game game = getGame(gameId);

        for (String fieldAndValueString : editInfo) {
            String[] fieldAndValueArray = fieldAndValueString.split("=");
            String fieldNameToEdit = fieldAndValueArray[0];

            String value = fieldAndValueArray[1];
            switch (fieldNameToEdit) {
                case "title" -> game.setTitle(value);
                case "price" -> {
                    BigDecimal price = new BigDecimal(value);
                    game.setPrice(price);
                }
                case "size" -> {
                    Double size = Double.parseDouble(value);
                    game.setSize(size);
                }
                case "trailer" -> game.setTrailer(value);
                case "thumbnail" -> game.setImageThumbnail(value);
                case "description" -> game.setDescription(value);
                case "releaseDate" ->
                        game.setReleaseDate(LocalDate.parse(value, DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            }
        }
        gameRepository.save(game);
    }

    @Override
    public void deleteGame(long gameId) {
        gameRepository.deleteById(gameId);
    }

    public Game getGame(long gameId) {
        Game game = gameRepository.findById(gameId).orElse(null);

        if (game == null) {
            System.out.println("There is no game with such ID");
            return null;
        }
        return game;
    }

    @Override
    public void printAllGamesInfo() {

        if (gameRepository.count() == 0) {
            System.out.println("There are no games added yet");
            return;
        }

        gameRepository.findAll()
                .stream()
                .map(game -> String.format("%s %.2f", game.getTitle(), game.getPrice()))
                .forEach(System.out::println);
    }

    @Override
    public void printGameDetails(String gameTitle) {
        Game game = gameRepository.findByTitle(gameTitle);

        if (game == null) {
            System.out.println("There is no game with such title");
            return;
        }

        String result = "Title: " + gameTitle + System.lineSeparator() +
                String.format("Price: %.2f", game.getPrice()) +
                System.lineSeparator() +
                "Description: " + game.getDescription() + System.lineSeparator() +
                "Release date: " + game.getReleaseDate();

        System.out.println(result);
    }

    @Override
    public void printUserGames(User user) {
        if (user.getGames().isEmpty()) {
            System.out.println("User " + user.getFullName() + " has no games yet.");
            return;
        }

        user.getGames()
            .stream()
            .map(Game::getTitle)
            .forEach(System.out::println);
    }
}
