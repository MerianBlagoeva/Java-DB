package bg.softuni.springrepositories;

import bg.softuni.springrepositories.entities.Ingredient;
import bg.softuni.springrepositories.entities.Shampoo;
import bg.softuni.springrepositories.entities.Size;
import bg.softuni.springrepositories.services.IngredientsService;
import bg.softuni.springrepositories.services.ShampooService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

@Component
public class Runner implements CommandLineRunner {

    private ShampooService shampooService;
    private IngredientsService ingredientsService;

    public Runner(ShampooService shampooService, IngredientsService ingredientsService) {
        this.shampooService = shampooService;
        this.ingredientsService = ingredientsService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Enter exercise number:");
        Scanner sc = new Scanner(System.in);

        int exerciseNumber = Integer.parseInt(sc.nextLine());

        switch (exerciseNumber) {
            case 0:
                List<Shampoo> byBrand = shampooService.findByBrand("Swiss Green Apple & Nettle");
                List<Shampoo> byBrandAndSize = shampooService.findByBrandAndSize("Swiss Green Apple & Nettle", Size.SMALL);
                byBrand.forEach(System.out::println);
                byBrandAndSize.forEach(System.out::println);
                break;
            case 1:
                List<Shampoo> bySize = shampooService.findBySizeOrderById(Size.MEDIUM);
                bySize.forEach(System.out::println);
                break;
            case 2:
                List<Shampoo> bySizeOrLabelId = shampooService.findBySizeOrLabelIdOrderByPrice(Size.MEDIUM, 10L);
                bySizeOrLabelId.forEach(System.out::println);
                break;
            case 3:
                List<Shampoo> byPriceGreaterThanOrderByPriceDesc = shampooService.findByPriceGreaterThanOrderByPriceDesc(BigDecimal.valueOf(5));
                byPriceGreaterThanOrderByPriceDesc.forEach(System.out::println);
                break;
            case 4:
                List<Ingredient> byNameStartingWith = ingredientsService.findByNameStartingWith("M");
                byNameStartingWith.forEach(System.out::println);
                break;
            case 5:
                List<Ingredient> findByNameWithin = ingredientsService.findByNameWithin(List.of("Lavender", "Herbs", "Apple"));
                findByNameWithin.forEach(System.out::println);
                break;
            case 6:
                int count = shampooService.findCheaperThanCount(BigDecimal.valueOf(8.50));
                System.out.println(count);
                break;
            case 7:
                List<Shampoo> result = shampooService.findAllWithIngredients(List.of("Berry", "Mineral-Collagen"));
                result.forEach(System.out::println);
                break;
            case 8:
                shampooService.findAllWithIngredientsCountLessThen(2);
                break;
            case 9:
                ingredientsService.deleteByName("Apple"); //Doesn't work
                break;
            case 10:
                ingredientsService.increasePrice(BigDecimal.valueOf(1.1));
                break;
            case 11:
                ingredientsService.updatePriceByName("Apple", BigDecimal.valueOf(1.1));
                break;
            default:
                throw new IllegalArgumentException("Invalid number of exercise");
        }
    }
}
