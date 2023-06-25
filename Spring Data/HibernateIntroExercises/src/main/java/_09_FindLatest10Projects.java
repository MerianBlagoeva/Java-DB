import entities.Project;

import javax.persistence.EntityManager;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

public class _09_FindLatest10Projects extends Engine {
    public _09_FindLatest10Projects(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public void run() {
        entityManager.createQuery("SELECT p FROM Project p ORDER BY p.startDate DESC", Project.class)
                .setMaxResults(10)
                .getResultStream()
                .sorted(Comparator.comparing(Project::getName))
                .forEach(p -> {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");

                    System.out.println("Project name:" + p.getName());
                    System.out.println("        Project Description:" + p.getDescription());
                    System.out.println("        Project Start Date:" + (p.getStartDate() != null ? p.getStartDate().format(formatter) : null));
                    System.out.println("        Project End Date:" + (p.getEndDate() != null ? p.getEndDate().format(formatter) : null));
                });
    }
}
