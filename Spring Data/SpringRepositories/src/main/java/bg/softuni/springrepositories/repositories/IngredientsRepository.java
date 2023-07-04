package bg.softuni.springrepositories.repositories;

import bg.softuni.springrepositories.entities.Ingredient;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface IngredientsRepository extends JpaRepository<Ingredient, Long> {

    List<Ingredient> findByNameStartingWith(String prefix);

    List<Ingredient> findByNameInOrderByPriceAsc(List<String> names);

    @Modifying
    @Transactional
    @Query("UPDATE Ingredient AS i SET i.price = i.price * :percentage")
    void increasePriceWithGivenPercentage(BigDecimal percentage);

    @Transactional
    void deleteIngredientsByName(String name);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Ingredient AS i SET i.price = i.price * :percentage WHERE i.name = :name")
    void updatePriceByName(String name, BigDecimal percentage);
}
