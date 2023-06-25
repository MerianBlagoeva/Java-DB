import entities.Employee;
import entities.Project;

import javax.persistence.EntityManager;

public class _08_GetEmployeeWithProject extends Engine {
    public _08_GetEmployeeWithProject(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public void run() {
        System.out.println("Enter employee id:");
        int id = Integer.parseInt(sc.nextLine());

        Employee employee = entityManager.find(Employee.class, id);

        System.out.printf("%s %s - %s%n",
                employee.getFirstName(), employee.getLastName(), employee.getJobTitle());

        employee.getProjects()
                .stream()
                .map(Project::getName)
                .sorted()
                .forEach(p -> System.out.println("      " + p));
    }
}
