package com.softuni.jsonexercise.repository;

import com.softuni.jsonexercise.model.entity.Category;
import com.softuni.jsonexercise.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u " +
            "WHERE (SELECT COUNT (p) FROM Product p WHERE p.seller.id = u.id) >= 1 " +
            "ORDER BY u.lastName, u.firstName")
    List<User> findAllUsersWithAtLeastOneSoldProductOrderByLastNameThenByFirstName();

    @Query("SELECT u FROM User u " +
            "WHERE (SELECT COUNT (p) FROM Product p WHERE p.seller.id = u.id) >= 1 ")
    List<User> findAllUsersWithAtLeastOneSoldProductOrderBySoldProductsDescThenByLastNameAsc();
}
