package com.softuni.springdataautomappingobjectsexercise.dtoEx.service.impl;

import com.softuni.springdataautomappingobjectsexercise.dtoEx.model.dto.GameAddDto;
import com.softuni.springdataautomappingobjectsexercise.dtoEx.model.entity.Game;
import com.softuni.springdataautomappingobjectsexercise.dtoEx.repository.GameRepository;
import com.softuni.springdataautomappingobjectsexercise.dtoEx.service.GameService;
import com.softuni.springdataautomappingobjectsexercise.dtoEx.util.ValidationUtil;
import jakarta.validation.ConstraintViolation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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

        //TODO: save in DB
    }
}
