package com.softuni.springautomapex.gamestore.service;

import com.softuni.springautomapex.gamestore.model.entity.Game;

import java.util.Set;

public interface OrderService {
    void addItem(String title);
    void removeItem(String title);
    Set<Game> buy();
}
