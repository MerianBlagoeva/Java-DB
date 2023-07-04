package bg.softuni.springrepositories.services;

import bg.softuni.springrepositories.entities.Ingredient;
import bg.softuni.springrepositories.repositories.IngredientsRepository;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.util.List;


public class IngredientsServiceImpl implements IngredientsService {
    private IngredientsRepository ingredientsRepository;

    public IngredientsServiceImpl(IngredientsRepository ingredientsRepository) {
        this.ingredientsRepository = ingredientsRepository;
    }

    @Override
    @Transactional
    public List<Ingredient> findByNameStartingWith(String prefix) {
        List<Ingredient> ingredients = ingredientsRepository.findByNameStartingWith(prefix);

        for (Ingredient ingredient : ingredients) {
            System.out.printf("%s - %d%n", ingredient.getName(), ingredient.getShampoos().size());
        }

        return ingredients;
    }

    @Override
    public List<Ingredient> findByNameWithin(List<String> names) {
        return ingredientsRepository.findByNameInOrderByPriceAsc(names);
    }

    @Override
    public void increasePrice(BigDecimal percentage) {
        ingredientsRepository.increasePriceWithGivenPercentage(percentage);
    }

    @Override
    public void deleteByName(String name) {
        ingredientsRepository.deleteIngredientsByName(name);
    }

    @Override
    public void updatePriceByName(String name, BigDecimal percentage) {
        ingredientsRepository.updatePriceByName(name, percentage);
    }
}
