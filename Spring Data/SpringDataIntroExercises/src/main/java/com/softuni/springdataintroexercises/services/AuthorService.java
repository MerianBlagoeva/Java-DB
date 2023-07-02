package com.softuni.springdataintroexercises.services;

import com.softuni.springdataintroexercises.models.Author;

import java.io.IOException;
import java.util.List;

public interface AuthorService {
    void seedAuthors() throws IOException;

    Author getRandomAuthor();

    List<String> getAllAuthorsOrderByBookCount();
}
