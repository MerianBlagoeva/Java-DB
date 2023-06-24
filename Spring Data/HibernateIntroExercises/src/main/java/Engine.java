import javax.persistence.EntityManager;
import java.util.Scanner;

public abstract class Engine implements Runnable {

    protected final EntityManager entityManager;
    protected Scanner sc;

    public Engine(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.sc = new Scanner(System.in);
    }

    @Override
    public abstract void run();
}
