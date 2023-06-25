import entities.Employee;

import javax.persistence.EntityManager;

public class _05_EmployeesFromDepartment extends Engine {
    public _05_EmployeesFromDepartment(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public void run() {
        entityManager.createQuery("SELECT e FROM Employee e " +
                "WHERE e.department.name = :d_name " +
                "ORDER BY e.salary, e.id", Employee.class)
                .setParameter("d_name", "Research and Development")
                .getResultStream()
                .forEach(e -> System.out.printf("%s %s from %s - $%.2f%n",
                        e.getFirstName(),
                        e.getLastName(),
                        e.getDepartment().getName(),
                        e.getSalary()));
    }
}
