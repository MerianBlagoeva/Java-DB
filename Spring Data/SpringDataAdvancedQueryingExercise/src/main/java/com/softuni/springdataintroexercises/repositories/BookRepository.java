package com.softuni.springdataintroexercises.repositories;

import com.softuni.springdataintroexercises.models.AgeRestriction;
import com.softuni.springdataintroexercises.models.Book;
import com.softuni.springdataintroexercises.models.EditionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByReleaseDateAfter(LocalDate releaseDateAfter);

    List<Book> findAllByReleaseDateBefore(LocalDate releaseDateBefore);

    List<Book> findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle(String author_firstName, String author_lastName);

    List<Book> findAllByAgeRestriction(AgeRestriction ageRestriction);

    List<Book> findAllByEditionTypeAndCopiesLessThan(EditionType editionType, Integer copies);

    List<Book> findAllByPriceLessThanOrPriceGreaterThan(BigDecimal lowerThan, BigDecimal higherThan);

    List<Book> findAllByReleaseDateBeforeOrReleaseDateAfter(LocalDate lower, LocalDate upper);

    List<Book> findAllByTitleContaining(String string);

    List<Book> findAllByAuthor_LastNameStartingWith(String prefix);

    @Query("SELECT COUNT(b) FROM Book b WHERE LENGTH(b.title) > :titleLength")
    int countOfBookWitTitleLengthMoreThan(int titleLength);

    @Procedure("change_book_price_by_id")
    void changeBookPriceById(Long book_id);

    @Modifying
    @Query("UPDATE Book b SET b.copies = b.copies + :copies WHERE b.releaseDate > :date")
    int updateCopiesByReleaseDate(@Param(value = "copies") int copies,
                                  @Param(value = "date") LocalDate date);


    Book getBookByTitle(String title);

    int removeAllByCopiesLessThan(Integer copies);

    //    @Procedure("usp_get_number_of_books_By_author_names") this doesn't work

    @Procedure(name = "Book.getTotalBooksByAuthorName")
    int getTotalNumberOfBooksByAuthorName(@Param(value = "first_name") String firstName,
                                          @Param(value = "last_name") String lastName);

}
