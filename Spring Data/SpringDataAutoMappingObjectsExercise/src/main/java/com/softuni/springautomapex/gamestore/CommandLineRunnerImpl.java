package com.softuni.springautomapex.gamestore;

import com.softuni.springautomapex.gamestore.model.dto.GameAddDto;
import com.softuni.springautomapex.gamestore.model.dto.UserLoginDto;
import com.softuni.springautomapex.gamestore.model.dto.UserRegisterDto;
import com.softuni.springautomapex.gamestore.model.entity.User;
import com.softuni.springautomapex.gamestore.service.GameService;
import com.softuni.springautomapex.gamestore.service.OrderService;
import com.softuni.springautomapex.gamestore.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Scanner;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final Scanner sc;
    private final UserService userService;
    private final GameService gameService;
    private final OrderService orderService;

    public CommandLineRunnerImpl(UserService userService, GameService gameService, OrderService orderService) {
        this.userService = userService;
        this.gameService = gameService;
        this.orderService = orderService;
        this.sc = new Scanner(System.in);
    }

    @Override
    public void run(String... args)  {
        while (true) {
            System.out.println("Enter your command:");
            String[] commandParts = sc.nextLine().split("\\|");

            switch (commandParts[0]) {
                case "RegisterUser" -> registerUser(commandParts);
                case "LoginUser" -> login(commandParts);
                case "Logout" -> logout();
                case "AddGame" -> addGame(commandParts);
                case "EditGame" -> editGame(commandParts);
                case "DeleteGame" -> deleteGame(commandParts);
                case "AllGames" -> printAllGames();
                case "DetailGame" -> printGameDetails(commandParts);
                case "OwnedGames" -> printOwnedGames();

                //Shopping Cart
                case "AddItem" -> orderService.addItem(commandParts[1]);
                case "RemoveItem" -> orderService.removeItem(commandParts[1]);
                case "BuyItem" -> buyItem();
            }
        }
    }

    private void buyItem() {
        if (orderService.buy().isEmpty()) {
            System.out.println("There's nothing in the shopping cart to buy.");
        } else {
            System.out.println("Successfully bought games:");
            orderService.buy().forEach(System.out::println);
        }

    }

    private void logout() {
        userService
                .logout();
    }

    private void login(String[] commandParts) {
        userService
                .login(new UserLoginDto(commandParts[1], commandParts[2]));
    }

    private void registerUser(String[] commandParts) {
        userService.register(
                new UserRegisterDto(commandParts[1], commandParts[2],
                        commandParts[3], commandParts[4]));
    }

    private void addGame(String[] commandParts) {

        if (!validateUser()) {
            return;
        };

        if (!isUserAdmin()) {
            return;
        }

        gameService.addGame(new GameAddDto(
                commandParts[1], new BigDecimal(commandParts[2]),
                Double.parseDouble(commandParts[3]),
                commandParts[4], commandParts[5], commandParts[6],
                commandParts[7]
        ));
    }

    private void printOwnedGames() {
        if (!validateUser()) {
            return;
        };

        gameService.printUserGames(userService.getCurrentUser());
    }

    private void printGameDetails(String[] commandParts) {

        if (!validateUser()) {
            return;
        };

        gameService.printGameDetails(commandParts[1]);
    }

    private void printAllGames() {

        if (!validateUser()) {
            return;
        };

        gameService.printAllGamesInfo();
    }

    private void deleteGame(String[] commandParts) {
        if (!validateUser()) {
            return;
        };

        if (!isUserAdmin()) {
            return;
        }

        gameService.deleteGame(Long.parseLong(commandParts[1]));
    }

    private void editGame(String[] commandParts) {
        if (!validateUser()) {
            return;
        };

        if (!isUserAdmin()) {
            return;
        }

        gameService
                .editGame(Long.parseLong(commandParts[1]), Arrays.copyOfRange(commandParts, 2, commandParts.length));
    }

    private boolean validateUser() {
        User user = userService.getCurrentUser();

        if (user == null) {
            System.out.println("There's no logged in user");
            return false;
        }
        return true;
    }

    private boolean isUserAdmin() {
        User user = userService.getCurrentUser();

        if (!user.isAdmin()) {
            System.out.println("User " + user.getFullName() + "is not an admin and can't do this operation.");
            return false;
        }

        return true;
    }
}
