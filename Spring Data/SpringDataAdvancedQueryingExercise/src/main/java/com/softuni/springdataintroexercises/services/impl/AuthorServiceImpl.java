package com.softuni.springdataintroexercises.services.impl;

import com.softuni.springdataintroexercises.models.Author;
import com.softuni.springdataintroexercises.models.Book;
import com.softuni.springdataintroexercises.repositories.AuthorRepository;
import com.softuni.springdataintroexercises.services.AuthorService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {
    private static final String AUTHORS_FILE_PATH = "src/main/resources/files/authors.txt";

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public void seedAuthors() throws IOException {
        if (authorRepository.count() > 0) {
            return;
        }

        Files.readAllLines(Path.of(AUTHORS_FILE_PATH))
                .forEach(row -> {
                    String[] fullName = row.split("\\s+");
                    Author author = new Author(fullName[0], fullName[1]);

                    authorRepository.save(author);
                });
    }

    @Override
    public Author getRandomAuthor() {
        long randomId = ThreadLocalRandom
                .current().nextLong(1, authorRepository.count() + 1);

        return authorRepository
                .findById(randomId)
                .orElse(null);
    }

    @Override
    public List<String> getAllAuthorsOrderByBookCount() {
        return authorRepository
                .findAllByBooksSizeDESC()
                .stream()
                .map(author -> String.format("%s %s %d",
                        author.getFirstName(),
                        author.getLastName(),
                        author.getBooks().size()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAuthorsFirstNameEndingWithSuffix(String suffix) {
        return authorRepository.findAllByFirstNameEndingWith(suffix)
                .stream()
                .map(author -> String.format("%s %s",
                        author.getFirstName(), author.getLastName()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllAuthorsAndTheirTotalNumberOfCopies() {
        return authorRepository.findAuthorsByTotalNumberOfCopies()
                .stream()
                .map(line -> {
                    String[] lineArray = line.split(",");
                    return String.format("%s %s - %d", lineArray[0], lineArray[1], Integer.parseInt(lineArray[2]));
                })
                .collect(Collectors.toList());
    }

    /*
     10 Alternative solution without the sorting

    @Override
    public List<String> findAllAuthorsAndTheirTotalNumberOfCopies() {
        return authorRepository
                .findAll()
                .stream()
                .map(author -> String.format("%s %s - %d",
                        author.getFirstName(),
                        author.getLastName(),
                        author.getBooks()
                                .stream()
                                .mapToInt(Book::getCopies)
                                .sum()))
                .collect(Collectors.toList());
    }

     */
}
