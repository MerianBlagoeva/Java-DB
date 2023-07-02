package com.softuni.springdataintroexercises;

import com.softuni.springdataintroexercises.models.Book;
import com.softuni.springdataintroexercises.services.AuthorService;
import com.softuni.springdataintroexercises.services.BookService;
import com.softuni.springdataintroexercises.services.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Scanner;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;

    public CommandLineRunnerImpl(CategoryService categoryService, AuthorService authorService, BookService bookService) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {
        seedData();

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter query number:");
        int queryNumber = Integer.parseInt(sc.nextLine());

        switch (queryNumber) {
            case 1 -> printAllBooksAfterYear(2000);
            case 2 -> printAllAuthorsNamesWithBooksWithReleaseDateBeforeYear(1990);
            case 3 -> printAllAuthorsAndNumberOfTheirBooks();
            case 4 -> printAllBooksByAuthorNameOrderByReleaseDate("George", "Powell");
            default -> System.out.println("Invalid number of query");
        }
    }

    private void printAllBooksByAuthorNameOrderByReleaseDate(String firstName, String lastName) {
        bookService.findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(firstName, lastName)
                .forEach(System.out::println);
    }

    private void printAllAuthorsAndNumberOfTheirBooks() {
        authorService
                .getAllAuthorsOrderByBookCount()
                .forEach(System.out::println);
    }

    private void printAllAuthorsNamesWithBooksWithReleaseDateBeforeYear(int year) {
        bookService
                .findAllAuthorsWithBooksWithReleaseDateBeforeYear(1999)
                .forEach(System.out::println);
    }

    private void printAllBooksAfterYear(int year) {
        bookService.findAllBooksAfterYear(year)
                .stream()
                .map(Book::getTitle)
                .forEach(System.out::println);
    }

    private void seedData() throws IOException {
        categoryService.seedCategories();
        authorService.seedAuthors();
        bookService.seedBooks();
    }
}
