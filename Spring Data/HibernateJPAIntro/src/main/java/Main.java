import entities.Student;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("hibernate-jpa");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Student student = new Student("TeoNew", 10.00, "TUES");
        em.persist(student);

        student.setName("TeoUpdated");
        em.persist(student);

//        Student findStudent = em.find(Student.class, 4);
//        em.remove(findStudent);
//
//        System.out.println(findStudent.getId());

        List<Student> fromStudent = em.createQuery("FROM Student", Student.class).getResultList();

        fromStudent.forEach(System.out::println);

        em.getTransaction().commit();
    }
}
