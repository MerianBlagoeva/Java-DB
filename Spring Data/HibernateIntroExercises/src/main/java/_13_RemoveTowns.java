import entities.Address;
import entities.Town;

import javax.persistence.EntityManager;
import java.util.List;

public class _13_RemoveTowns extends Engine {
    public _13_RemoveTowns(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public void run() {
        System.out.println("Enter town name:");
        String townName = sc.nextLine();

        Town town = entityManager
                .createQuery("SELECT t FROM Town t WHERE t.name = :t_name",
                        Town.class)
                .setParameter("t_name", townName)
                .getSingleResult();

        int affectedRows = removeAddressesByTownId(town.getId());

        entityManager.getTransaction().begin();
        entityManager.remove(town);
        entityManager.getTransaction().commit();

        System.out.printf("%d addresses in %s deleted", affectedRows, townName);
    }

    private int removeAddressesByTownId(Integer id) {

        List<Address> addresses = entityManager
                .createQuery("SELECT a FROM Address a " +
                        "WHERE a.town.id = :p_id", Address.class)
                .setParameter("p_id", id)
                .getResultList();

        entityManager.getTransaction().begin();
        addresses.forEach(entityManager::remove);
        entityManager.getTransaction().commit();

        return addresses.size();

    }
}
