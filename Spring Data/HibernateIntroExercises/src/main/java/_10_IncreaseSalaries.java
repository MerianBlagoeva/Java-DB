import entities.Employee;

import javax.persistence.EntityManager;
import java.util.Set;

public class _10_IncreaseSalaries extends Engine {
    public _10_IncreaseSalaries(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public void run() {
        entityManager.getTransaction().begin();
        Set<Integer> departments = Set.of(1, 2, 4, 11);

        entityManager
                .createQuery("UPDATE Employee e " +
                        "SET e.salary = e.salary * 1.12 " +
                        "WHERE e.department.id IN :IDs")
                .setParameter("IDs", departments)
                .executeUpdate();

        entityManager.getTransaction().commit();

        entityManager
                .createQuery("SELECT e FROM Employee e " +
                        "WHERE e.department.id IN :IDs", Employee.class)
                .setParameter("IDs", departments)
                .getResultStream()
                .forEach(e -> System.out.printf("%s %s ($%.2f)%n",
                        e.getFirstName(), e.getLastName(), e.getSalary()));

    }
}
