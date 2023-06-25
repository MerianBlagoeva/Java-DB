import entities.Employee;

import javax.persistence.EntityManager;

public class _11_FindEmployeesByFirstName extends Engine {
    public _11_FindEmployeesByFirstName(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public void run() {
        System.out.println("Enter pattern:");
        String pattern = sc.nextLine();

        entityManager
                .createQuery("SELECT e FROM Employee e " +
                        "WHERE e.firstName LIKE CONCAT(:pattern, '%')", Employee.class)
                .setParameter("pattern", pattern)
                .getResultStream()
                .forEach(e -> System.out.printf("%s %s - %s - ($%.2f)%n",
                        e.getFirstName(), e.getLastName(), e.getJobTitle(), e.getSalary()));
    }
}
