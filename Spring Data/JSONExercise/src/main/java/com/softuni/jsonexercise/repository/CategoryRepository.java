package com.softuni.jsonexercise.repository;

import com.softuni.jsonexercise.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c FROM Category c " +
            "JOIN c.products p " +
            "GROUP BY c " +
            "ORDER BY COUNT(p) DESC")
    List<Category> findAllOrderByNumberOfProducts();


}
