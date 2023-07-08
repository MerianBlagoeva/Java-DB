package com.softuni.springdataautomappingobjectsexercise.dtoEx.repository;

import com.softuni.springdataautomappingobjectsexercise.dtoEx.model.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
}
