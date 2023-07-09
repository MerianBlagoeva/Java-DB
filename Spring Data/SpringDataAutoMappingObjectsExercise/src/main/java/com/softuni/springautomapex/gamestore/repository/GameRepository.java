package com.softuni.springautomapex.gamestore.repository;

import com.softuni.springautomapex.gamestore.model.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    Game findByTitle(String title);
}
