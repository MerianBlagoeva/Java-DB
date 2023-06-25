import javax.persistence.EntityManager;

public class ExerciseFactory {
    public static Engine createExercise(int exerciseNumber, EntityManager entityManager) {
        switch (exerciseNumber) {
            case 2:
                return new _02_ChangeCasing(entityManager);
            case 3:
                return new _03_ContainsEmployee(entityManager);
            case 4:
                return new _04_EmployeesWithSalaryOver50000(entityManager);
            case 5:
                return new _05_EmployeesFromDepartment(entityManager);
            case 6:
                return new _06_AddingNewAddressAndUpdatingEmployee(entityManager);
            case 7:
                return new _07_AddressesWithEmployeeCount(entityManager);
            case 8:
                return new _08_GetEmployeeWithProject(entityManager);
            case 9:
                return new _09_FindLatest10Projects(entityManager);
            case 10:
                return new _10_IncreaseSalaries(entityManager);
            case 11:
                return new _11_FindEmployeesByFirstName(entityManager);
            case 12:
                return new _12_EmployeesMaximumSalaries(entityManager);
            case 13:
                return new _13_RemoveTowns(entityManager);
            default:
                return null;
        }

    }
}

