import javax.persistence.EntityManager;
import javax.persistence.Query;

public class _02_ChangeCasing extends Engine {
    public _02_ChangeCasing(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public void run() {
        entityManager.getTransaction().begin();

        Query query = entityManager.createQuery("UPDATE Town t " +
                "SET t.name = UPPER(t.name) " +
                "WHERE LENGTH(t.name) < 5 ");

        System.out.println(query.executeUpdate());

        entityManager.getTransaction().commit();
    }
}
