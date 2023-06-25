import entities.Employee;

import javax.persistence.EntityManager;
import java.math.BigDecimal;

public class _04_EmployeesWithSalaryOver50000 extends Engine {
    public _04_EmployeesWithSalaryOver50000(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public void run() {
        entityManager.createQuery("SELECT e FROM Employee e " +
                        "WHERE e.salary > :min_salary", Employee.class)
                .setParameter("min_salary", BigDecimal.valueOf(50000L))
                .getResultStream()
                .map(Employee::getFirstName)
                .forEach(System.out::println);
    }
}
