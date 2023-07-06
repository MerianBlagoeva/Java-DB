package com.softuni.springdataintroexercises.services;

import com.softuni.springdataintroexercises.models.AgeRestriction;
import com.softuni.springdataintroexercises.models.Book;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface BookService {
    void seedBooks() throws IOException;

    List<Book> findAllBooksAfterYear(int year);

    List<String> findAllAuthorsWithBooksWithReleaseDateBeforeYear(int year);

    List<String> findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(String firstName, String lastName);

    List<String> findAllBookTitlesWithAgeRestriction(AgeRestriction ageRestriction);

    List<String> findAllGoldenBookTitlesWithCopiesLessThan5000();

    List<String> findAllBookTitlesWithPriceLowerThan5OrHigherThan40();

    List<String> findNotReleasedBookTitlesInYear(int year);

    List<String> findAllBooksReleasedBeforeAGivenDate(LocalDate date);

    List<String> findAllBookTitlesContainingAGivenString(String stringToBeSearched);

    List<String> findAllBookTitlesWithAuthorLastNameStartingWith(String prefix);

    int findBooksCountWithTitleLengthLongerThan(int length);

    void changePrice(long bookId);


    int increaseCopiesByDate(LocalDate date, int copies);

    String getBookInfo(String bookTitle);


    int removeBooksWithCopiesLessThanANumber(int copiesNumber);

    int getTotalNumberOfBooksByAuthorNames(String firstName, String lastName);
}
