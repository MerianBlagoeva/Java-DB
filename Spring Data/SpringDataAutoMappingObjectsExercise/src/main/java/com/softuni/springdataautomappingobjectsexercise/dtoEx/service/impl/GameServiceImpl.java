package com.softuni.springdataautomappingobjectsexercise.dtoEx.service.impl;

import com.softuni.springdataautomappingobjectsexercise.dtoEx.model.dto.GameAddDto;
import com.softuni.springdataautomappingobjectsexercise.dtoEx.model.entity.Game;
import com.softuni.springdataautomappingobjectsexercise.dtoEx.repository.GameRepository;
import com.softuni.springdataautomappingobjectsexercise.dtoEx.service.GameService;
import com.softuni.springdataautomappingobjectsexercise.dtoEx.util.ValidationUtil;
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
    public void printGameInfo(String gameTitle) {
        Game game = gameRepository.findByTitle(gameTitle);

        if (game == null) {
            System.out.println("There is no game with such title");
            return;
        }

        String sb = "Title: " + gameTitle + System.lineSeparator() +
                String.format("Price: %.2f", game.getPrice()) +
                System.lineSeparator() +
                "Description: " + game.getDescription() + System.lineSeparator() +
                "Release date: " + game.getReleaseDate();

        System.out.println(sb);
    }


}
