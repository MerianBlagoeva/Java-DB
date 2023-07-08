package com.softuni.springdataautomappingobjectsexercise.dtoEx;

import com.softuni.springdataautomappingobjectsexercise.dtoEx.model.dto.GameAddDto;
import com.softuni.springdataautomappingobjectsexercise.dtoEx.model.dto.UserLoginDto;
import com.softuni.springdataautomappingobjectsexercise.dtoEx.model.dto.UserRegisterDto;
import com.softuni.springdataautomappingobjectsexercise.dtoEx.service.GameService;
import com.softuni.springdataautomappingobjectsexercise.dtoEx.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Scanner;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final Scanner sc;
    private final UserService userService;
    private final GameService gameService;

    public CommandLineRunnerImpl(UserService userService, GameService gameService) {
        this.userService = userService;
        this.gameService = gameService;
        this.sc = new Scanner(System.in);
    }

    @Override
    public void run(String... args) throws Exception {
        while (true) {
            System.out.println("Enter your command:");
            String[] commandParts = sc.nextLine().split("\\|");

            switch (commandParts[0]) {
                case "RegisterUser" -> userService.registerUser(
                        new UserRegisterDto(commandParts[1], commandParts[2],
                                commandParts[3], commandParts[4]));
                case "LoginUser" -> userService
                        .loginUser(new UserLoginDto(commandParts[1], commandParts[2]));
                case "Logout" -> userService
                        .logout();
                case "AddGame" -> gameService.addGame(new GameAddDto(
                        commandParts[1], new BigDecimal(commandParts[2]),
                        Double.parseDouble(commandParts[3]),
                        commandParts[4], commandParts[5], commandParts[6],
                        commandParts[7]
                ));
            }
        }
    }
}
