package bg.softuni.springrepositories.services;

import bg.softuni.springrepositories.entities.Shampoo;
import bg.softuni.springrepositories.entities.Size;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface ShampooService {

    List<Shampoo> findByBrand(String brand); //0

    List<Shampoo> findByBrandAndSize(String brand, Size size); //0

    List<Shampoo> findBySizeOrderById(Size size); //1

    List<Shampoo> findBySizeOrLabelIdOrderByPrice(Size size, long labelId); //2

    List<Shampoo> findByPriceGreaterThanOrderByPriceDesc(BigDecimal price);

    int findCheaperThanCount(BigDecimal bigDecimal);

    List<Shampoo> findAllWithIngredients(List<String> ingredientNames);

    List<Shampoo> findAllWithIngredientsCountLessThen(int ingredientsCount);
}
