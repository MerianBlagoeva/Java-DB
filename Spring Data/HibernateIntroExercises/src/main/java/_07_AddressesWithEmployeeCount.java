import entities.Address;

import javax.persistence.EntityManager;

public class _07_AddressesWithEmployeeCount extends Engine {
    public _07_AddressesWithEmployeeCount(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public void run() {
        entityManager
                .createQuery("SELECT a FROM Address a " +
                        "ORDER BY SIZE(a.employees) DESC ", Address.class)
                .setMaxResults(10)
                .getResultStream()
                .forEach(a -> System.out.printf("%s, %s - %d employees%n",
                        a.getText(),
                        a.getTown() == null ? "Unknown" : a.getTown().getName(),
                        a.getEmployees().size()));
    }
}
