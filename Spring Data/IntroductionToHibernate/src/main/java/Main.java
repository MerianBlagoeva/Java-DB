import entities.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Configuration cfg = new Configuration();
        cfg.configure();
        SessionFactory sessionFactory =
                cfg.buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Student student = new Student("Pesho2", 15);
        session.save(student);

        Student student1 = session.get(Student.class, 1);
        System.out.println(student1);

        List<Student> students = session
                .createQuery("FROM Student WHERE name = 'Pesho2'",
                Student.class).list();

        students.forEach(System.out::println);

        // Your Code Here

        session.getTransaction().commit();
        session.close();
    }
}
