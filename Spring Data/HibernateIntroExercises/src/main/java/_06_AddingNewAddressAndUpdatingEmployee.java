import entities.Address;
import entities.Employee;

import javax.persistence.EntityManager;

public class _06_AddingNewAddressAndUpdatingEmployee extends Engine {
    public _06_AddingNewAddressAndUpdatingEmployee(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public void run() {
        System.out.println("Enter employee last name");
        String lastName = sc.nextLine();

        Employee employee = entityManager
                .createQuery("SELECT e FROM Employee e " +
                        "WHERE e.lastName = :last_name", Employee.class)
                .setParameter("last_name", lastName)
                .getSingleResult();

        Address address = createAddress();

        entityManager.getTransaction().begin();
        employee.setAddress(address);
        entityManager.getTransaction().commit();

    }

    private Address createAddress() {
        Address address = new Address();
        address.setText("Vitoshka 15");

        entityManager.getTransaction().begin();
        entityManager.persist(address);
        entityManager.getTransaction().commit();

        return address;
    }
}
