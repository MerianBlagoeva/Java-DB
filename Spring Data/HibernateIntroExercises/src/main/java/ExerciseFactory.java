import javax.persistence.EntityManager;

public class ExerciseFactory {
    public static Engine createExercise(int exerciseNumber, EntityManager entityManager) {
        switch (exerciseNumber) {
            case 2:
                return new ChangeCasing(entityManager);
            case 3:
                return new ContainsEmployee(entityManager);
            default: return null;
        }

    }
}

