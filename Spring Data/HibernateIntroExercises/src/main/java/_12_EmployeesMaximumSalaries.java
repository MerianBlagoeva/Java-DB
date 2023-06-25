import javax.persistence.EntityManager;
import java.math.BigDecimal;

public class _12_EmployeesMaximumSalaries extends Engine {
    public _12_EmployeesMaximumSalaries(EntityManager entityManager) {
        super(entityManager);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void run() {
        entityManager
                .createNativeQuery("SELECT d.name, MAX(e.salary) AS max_salary\n" +
                        "FROM departments d\n" +
                        "JOIN employees e on d.department_id = e.department_id\n" +
                        "GROUP BY d.name\n" +
                        "HAVING max_salary NOT BETWEEN 30000 AND 70000;")
                .getResultStream()
                .forEach(result -> {
                    Object[] row = (Object[]) result;
                    String departmentName = (String) row[0];
                    BigDecimal maxSalary = (BigDecimal) row[1];
                    System.out.println(departmentName + " " + maxSalary);
                });


    }
}
