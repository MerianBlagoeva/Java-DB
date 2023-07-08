package com.softuni.springdataautomappingobjectsexercise.dtoEx.repository;

import com.softuni.springdataautomappingobjectsexercise.dtoEx.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmailAndPassword(String email, String password);
}
