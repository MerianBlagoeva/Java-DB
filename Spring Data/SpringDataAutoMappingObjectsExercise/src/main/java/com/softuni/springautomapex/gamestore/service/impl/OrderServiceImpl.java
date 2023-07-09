package com.softuni.springautomapex.gamestore.service.impl;

import com.softuni.springautomapex.gamestore.model.entity.Game;
import com.softuni.springautomapex.gamestore.model.entity.Order;
import com.softuni.springautomapex.gamestore.model.entity.User;
import com.softuni.springautomapex.gamestore.repository.GameRepository;
import com.softuni.springautomapex.gamestore.repository.OrderRepository;
import com.softuni.springautomapex.gamestore.service.OrderService;
import com.softuni.springautomapex.gamestore.service.UserService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class OrderServiceImpl implements OrderService {

    private final UserService userService;
    private final GameRepository gameRepository;
    private final OrderRepository orderRepository;

    public OrderServiceImpl(GameRepository gameRepository, UserService userService, OrderRepository orderRepository) {
        this.userService = userService;
        this.gameRepository = gameRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public void addItem(String title) {
        Game game = gameRepository.findByTitle(title);
        User user = userService.getCurrentUser();

        if (game == null) {
            System.out.println("There is no game with such title.");
            return;
        }

        if (user == null) {
            System.out.println("You need to be logged in to do this operation.");
            return;
        }


        Order order;
        if (user.getOrder() == null) {
            order = new Order();
            order.setBuyer(user);
            user.setOrder(order);
        } else {
            order = user.getOrder();
        }


        order.getBuyer().getOrder().getGames().add(game);

        System.out.printf("%s added to cart.%n", game.getTitle());

        orderRepository.save(order);
    }

    @Override
    public void removeItem(String title) {
        Game game = gameRepository.findByTitle(title);
        User user = userService.getCurrentUser();
        Order order = user.getOrder() != null ? user.getOrder() : new Order();
        order.getGames().remove(game);
        orderRepository.save(order);

        System.out.println(game.getTitle() + " removed from cart.");
    }

    @Override
    public Set<Game> buy() {
        User user = userService.getCurrentUser();
        Order order = user.getOrder();

        if (order == null)
            return new HashSet<>();

        Set<Game> games = user.getOrder().getGames();

        order.getGames().forEach(game -> user.getGames().add(game));

        orderRepository.delete(order);
        return games;
    }
}
