package com.softuni.springdataautomappingobjectsexercise.dtoEx.service.impl;

import com.softuni.springdataautomappingobjectsexercise.dtoEx.model.dto.UserLoginDto;
import com.softuni.springdataautomappingobjectsexercise.dtoEx.model.dto.UserRegisterDto;
import com.softuni.springdataautomappingobjectsexercise.dtoEx.model.entity.Game;
import com.softuni.springdataautomappingobjectsexercise.dtoEx.model.entity.User;
import com.softuni.springdataautomappingobjectsexercise.dtoEx.repository.GameRepository;
import com.softuni.springdataautomappingobjectsexercise.dtoEx.repository.UserRepository;
import com.softuni.springdataautomappingobjectsexercise.dtoEx.service.UserService;
import com.softuni.springdataautomappingobjectsexercise.dtoEx.util.ValidationUtil;
import jakarta.validation.ConstraintViolation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private User loggedInUser;

    private final GameRepository gameRepository;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, ValidationUtil validationUtil, GameRepository gameRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gameRepository = gameRepository;
    }

    @Override
    public void registerUser(UserRegisterDto userRegisterDto) {
        if (!userRegisterDto.getPassword().equals(userRegisterDto.getConfirmPassword())) {
            System.out.println("Wrong confirm password");
        }

        Set<ConstraintViolation<UserRegisterDto>> violations =
                validationUtil.getViolations(userRegisterDto);

        if (!violations.isEmpty()) {
            violations
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);

            return;
        }

        User user = modelMapper.map(userRegisterDto, User.class);

        userRepository.save(user);
        System.out.println(user.getFullName() + " was registered");
        if (userRepository.count() == 1) {
            user.setAdmin(true);
        }

        System.out.println("User is admin: " + user.getAdmin());

    }

    @Override
    public void loginUser(UserLoginDto userLoginDto) {
        Set<ConstraintViolation<UserLoginDto>> violations = validationUtil.getViolations(userLoginDto);

        if (!violations.isEmpty()) {
            violations
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
            return;
        }

        User user = userRepository.findByEmailAndPassword(
                        userLoginDto.getEmail(), userLoginDto.getPassword())
                .orElse(null);

        if (user == null) {
            System.out.println("Incorrect username / password");
            return;
        }

        loggedInUser = user;
        System.out.println("Successfully logged in " + user.getFullName());
    }

    @Override
    public void logout() {
        if (loggedInUser != null) {
            System.out.printf("User %s successfully logged out%n", loggedInUser.getFullName());
            loggedInUser = null;
        } else {
            System.out.println("Cannot logout. No user was logged in.");
        }
    }

    @Override
    public void deleteGame(long gameId) {
        if (checkIfUserIsLoggedIn()) return;

        if (checkIfUserHasRights()) return;

        Game game = getGame(gameId);
        if (game == null) return;

        String gameTitle = game.getTitle();
        gameRepository.delete(game);
        System.out.println("Deleted " + gameTitle);
    }

    private boolean checkIfUserHasRights() {
        if (!hasUserRights()) {
            System.out.println("User " + loggedInUser.getFullName() + "has no rights to do this operation.");
            return true;
        }
        return false;
    }

    @Override
    public void editGame(Long gameId, String[] editInfo) {

        if (checkIfUserIsLoggedIn()) return;

        if (checkIfUserHasRights()) return;

        Game game = getGame(gameId);
        if (game == null) return;

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

        String gameTitle = game.getTitle();
        gameRepository.save(game);
        System.out.println("Edited " + gameTitle);


    }

    private boolean checkIfUserIsLoggedIn() {
        if (!isUserLoggedIn()) {
            System.out.println("You need to be logged in to do this operation.");
            return true;
        }
        return false;
    }

    @Override
    public void printOwnedGames() {
        if (checkIfUserIsLoggedIn()) return;

        if (checkIfUserHasRights()) return;

        if (loggedInUser.getGames() == null || loggedInUser.getGames().isEmpty()) {
            System.out.println("User " + loggedInUser.getFullName() + " has no games yet.");
            return;
        }

        loggedInUser.getGames()
                .stream()
                .map(Game::getTitle)
                .forEach(System.out::println);
    }

    private Game getGame(long gameId) {
        Game game = gameRepository.findById(gameId).orElse(null);

        if (game == null) {
            System.out.println("There is no game with such ID");
            return null;
        }
        return game;
    }

    private boolean isUserLoggedIn() {
        return loggedInUser != null;
    }

    private boolean hasUserRights() {
        return loggedInUser.getAdmin();
    }
}
