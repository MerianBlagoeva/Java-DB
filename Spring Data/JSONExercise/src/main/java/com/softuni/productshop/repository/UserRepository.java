package com.softuni.productshop.repository;

import com.softuni.productshop.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u " +
            "JOIN u.soldProducts p " +
            "WHERE p.buyer IS NOT NULL " +
            "ORDER BY u.lastName, u.firstName")
    List<User> findAllUsersWithAtLeastOneSoldProductOrderByLastNameThenByFirstName();

    @Query("SELECT u FROM User u " +
            "WHERE (SELECT COUNT (p) FROM Product p WHERE p.seller.id = u.id) >= 1 ")
    List<User> findAllUsersWithAtLeastOneSoldProductOrderBySoldProductsDescThenByLastNameAsc();
}
