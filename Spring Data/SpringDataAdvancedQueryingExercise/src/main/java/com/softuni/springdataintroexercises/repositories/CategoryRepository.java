package com.softuni.springdataintroexercises.repositories;

import com.softuni.springdataintroexercises.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
