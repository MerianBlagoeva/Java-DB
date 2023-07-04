package bg.softuni.springrepositories.services;

import bg.softuni.springrepositories.entities.Ingredient;

import java.math.BigDecimal;
import java.util.List;

public interface IngredientsService {
    List<Ingredient> findByNameStartingWith(String prefix);

    List<Ingredient> findByNameWithin(List<String> names);

    void increasePrice(BigDecimal percentage);

    void deleteByName(String name);

    void updatePriceByName(String name, BigDecimal percentage);
}
