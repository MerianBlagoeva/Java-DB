package com.softuni.springdataintroexercises.repositories;

import com.softuni.springdataintroexercises.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    @Query("SELECT a FROM Author a ORDER BY SIZE(a.books) DESC")
    List<Author> findAllByBooksSizeDESC();

    List<Author> findAllByFirstNameEndingWith(String suffix);

    @Query("SELECT a.firstName, a.lastName, sum(b.copies) AS total_copies " +
            "FROM Author a " +
            "JOIN Book b ON a.id = b.author.id " +
            "GROUP BY a.id " +
            "ORDER BY total_copies DESC ")
    List<String> findAuthorsByTotalNumberOfCopies();


}
