import entities.Employee;

import javax.persistence.EntityManager;

public class ContainsEmployee extends Engine {
    public ContainsEmployee(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public void run() {
        System.out.println("Enter employee full name:");
        String[] fullName = sc.nextLine().split("\\s+");
        String firstName = fullName[0];
        String lastName = fullName[1];

        Long singleResult = entityManager
                .createQuery("SELECT COUNT(e) FROM Employee e " +
                                "WHERE e.firstName = :f_name AND e.lastName = :l_name",
                        Long.class)
                .setParameter("f_name", firstName)
                .setParameter("l_name", lastName)
                .getSingleResult();

        System.out.println(singleResult == 0 ? "No" : "Yes");
    }
}
