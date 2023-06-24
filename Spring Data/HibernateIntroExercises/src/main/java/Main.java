import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("softuni");
        EntityManager entityManager = emf.createEntityManager();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter exercise number:");
        int exerciseNumber = Integer.parseInt(scanner.nextLine());

        Engine engine = ExerciseFactory.createExercise(exerciseNumber, entityManager);

        if (engine != null) {
            engine.run();
        } else {
            System.out.println("Invalid exercise number");
        }

        entityManager.close();
        emf.close();
    }
}

