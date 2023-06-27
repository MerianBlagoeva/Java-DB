import composition.CompositionCar;
import composition.PlateNumber;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;

public class CompositionMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("relations");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        CompositionCar car = new CompositionCar("Opel", 1200);
        CompositionCar car2 = new CompositionCar("Skoda", 1300);

        PlateNumber number = new PlateNumber("CB11119G", LocalDate.now());

        car.setPlateNumber(number);
        number.setCar(car2);

        em.persist(car2);
        em.persist(car);
        em.persist(number);

        em.find(CompositionCar.class, 5);

        em.getTransaction().commit();
    }
}
