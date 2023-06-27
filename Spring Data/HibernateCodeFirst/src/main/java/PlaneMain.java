import composition.Company;
import composition.Plane;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class PlaneMain {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("relations");
        EntityManager em = emf.createEntityManager();


        em.getTransaction().begin();


        Company company = new Company("noname");
        Plane plane = new Plane("SoftUniAir", 250);
        Plane plane2 = new Plane("SoftUniAir", 255);

        plane.setCompany(company);
        plane2.setCompany(company);

        em.persist(company);
        em.persist(plane);
        em.persist(plane2);

        Plane plane1 = em.find(Plane.class, 8);

        Company found = em.find(Company.class, 1);


        em.getTransaction().commit();

    }
}
