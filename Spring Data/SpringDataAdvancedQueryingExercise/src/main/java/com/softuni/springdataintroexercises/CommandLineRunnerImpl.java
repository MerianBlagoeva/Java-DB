package com.softuni.springdataintroexercises;

import com.softuni.springdataintroexercises.models.AgeRestriction;
import com.softuni.springdataintroexercises.models.Book;
import com.softuni.springdataintroexercises.services.AuthorService;
import com.softuni.springdataintroexercises.services.BookService;
import com.softuni.springdataintroexercises.services.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;
    private final Scanner sc;

    public CommandLineRunnerImpl(CategoryService categoryService, AuthorService authorService, BookService bookService, Scanner sc) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
        this.sc = sc;
    }

    @Override
    public void run(String... args) throws Exception {
        seedData();
//
//        System.out.println("Enter query number:");
//        int queryNumber = Integer.parseInt(sc.nextLine());
//
//        switch (queryNumber) {
//            case 1 -> printAllBooksAfterYear(2000);
//            case 2 -> printAllAuthorsNamesWithBooksWithReleaseDateBeforeYear(1990);
//            case 3 -> printAllAuthorsAndNumberOfTheirBooks();
//            case 4 -> printAllBooksByAuthorNameOrderByReleaseDate("George", "Powell");
//            default -> System.out.println("Invalid number of query");
//        }

        System.out.println("Enter exercise number (from Exercises):");
        int exerciseNumber = Integer.parseInt(sc.nextLine());

        switch (exerciseNumber) {
            case 1:
                booksTitlesByAgeRestriction();
                break;
            case 2:
                goldenBooks();
                break;
            case 3:
                booksByPrice();
                break;
            case 4:
                notReleasedBooks();
                break;
            case 5:
                booksReleasedBeforeDate();
                break;
            case 6:
                authorsSearch();
                break;
            case 7:
                booksSearch();
                break;
            case 8:
                bookTitlesSearch();
                break;
            case 9:
                countBooks();
                break;
            case 10:
                totalBookCopies();
                break;
            case 11:
                reducedBook();
                break;
            case 12:
                increaseBookCopies();
                break;
            case 13:
                removeBooks();
                break;
            case 14:
                storedProcedure();
                break;
            case 15:
                test();
                break;
            default:
                throw new IllegalStateException("Invalid number of exercise: " + exerciseNumber);
        }
    }

    private void storedProcedure() {
        System.out.println("Enter author's first and last name:");
        String[] authorNames = sc.nextLine().split("\\s+");
        String firstName = authorNames[0];
        String lastname = authorNames[1];
        int totalBooks = bookService.getTotalNumberOfBooksByAuthorNames(firstName, lastname);

        System.out.printf("%s %s has written %d books%n",
                firstName, lastname, totalBooks);
    }

    private void removeBooks() {
        System.out.println("Enter copies number: ");
        int copiesNumber = Integer.parseInt(sc.nextLine());

        System.out.printf("%d books were removed.",
                bookService.removeBooksWithCopiesLessThanANumber(copiesNumber));
    }

    private void reducedBook() {
        System.out.println("Enter book title:");
        String bookTitle = sc.nextLine();

        System.out.println(bookService.getBookInfo(bookTitle));
    }

    private void increaseBookCopies() {
        System.out.println("Enter a date in format dd MMM yyyy:");
        LocalDate date = LocalDate.parse(sc.nextLine(), DateTimeFormatter.ofPattern("dd MMM yyyy"));

        System.out.println("Enter number of copies:");
        int copies = Integer.parseInt(sc.nextLine());

        int affectedRows = bookService.increaseCopiesByDate(date, copies);
        System.out.printf("%d books are released after %s, so a total of %d book copies were added.%n",
                affectedRows,
                date.format(DateTimeFormatter.ofPattern("dd MMM yyyy")),
                affectedRows * copies);
    }

    private void test() {
        bookService.changePrice(1L);
    }

    private void totalBookCopies() {
//        authorService
//                .findAllAuthorsAndTheirTotalNumberOfCopies()
//                .forEach(System.out::println);

        authorService.findAllAuthorsAndTheirTotalNumberOfCopies()
                .forEach(System.out::println);
    }

    private void countBooks() {
        System.out.println("Enter title length");
        int length = Integer.parseInt(sc.nextLine());

        System.out.printf("There are %d books with longer titles than %d symbols%n",
                bookService.findBooksCountWithTitleLengthLongerThan(length), length);
    }

    private void bookTitlesSearch() {
        System.out.println("Please enter the prefix of the author's last name:");
        String prefix = sc.nextLine();

        bookService.findAllBookTitlesWithAuthorLastNameStartingWith(prefix)
                .forEach(System.out::println);
    }

    private void booksSearch() {
        System.out.println("Enter a string");
        String stringToBeSearched = sc.nextLine();

        bookService.findAllBookTitlesContainingAGivenString(stringToBeSearched.toLowerCase())
                .forEach(System.out::println);
    }

    private void authorsSearch() {
        System.out.println("Enter first name suffix:");
        String suffix = sc.nextLine();

        authorService.findAuthorsFirstNameEndingWithSuffix(suffix)
                .forEach(System.out::println);

    }

    private void booksReleasedBeforeDate() {
        System.out.println("Enter a date in format dd-MM-yyyy:");
        LocalDate date = LocalDate.parse(sc.nextLine(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        bookService.findAllBooksReleasedBeforeAGivenDate(date)
                .forEach(System.out::println);
    }

    private void notReleasedBooks() {
        System.out.println("Enter a year:");
        int year = Integer.parseInt(sc.nextLine());

        bookService.findNotReleasedBookTitlesInYear(year)
                .forEach(System.out::println);
    }

    private void booksByPrice() {
        bookService.findAllBookTitlesWithPriceLowerThan5OrHigherThan40()
                .forEach(System.out::println);
    }

    private void goldenBooks() {
        bookService.findAllGoldenBookTitlesWithCopiesLessThan5000()
                .forEach(System.out::println);
    }

    private void booksTitlesByAgeRestriction() {
        System.out.println("Enter age restriction:");
        AgeRestriction ageRestriction = AgeRestriction.valueOf(sc.nextLine().toUpperCase());

        bookService.findAllBookTitlesWithAgeRestriction(ageRestriction)
                .forEach(System.out::println);
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
