package _04_hospitalDatabase;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.Set;

public class HospitalDatabaseManager {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("test");
    EntityManager entityManager = emf.createEntityManager();

    public HospitalDatabaseManager() {
    }

    private Patient getPatientById(long patientId) {
        return entityManager.find(Patient.class, patientId);
    }


    public void printPatientInformation(Scanner scanner) {

        System.out.println("Enter patient ID:");
        long patientId = Long.parseLong(scanner.nextLine());

        Patient patient = getPatientById(patientId);

        if (patient != null) {
            System.out.println("Patient Information:");
            System.out.println("ID: " + patient.getId());
            System.out.println("First Name: " + patient.getFirstName());
            System.out.println("Last Name: " + patient.getLastName());
            System.out.println("Address: " + patient.getAddress());
            System.out.println("Email: " + patient.getEmail());
            System.out.println("Birth Date: " + patient.getBirthdate());
        } else {
            System.out.println("Patient not found with ID: " + patientId);
        }

    }

    public String getPatientHistory(Scanner scanner) {

        System.out.println("Enter patient ID:");
        long patientId = Long.parseLong(scanner.nextLine());

        Patient patient = getPatientById(patientId);

        StringBuilder result = new StringBuilder();

        if (patient != null) {
            Set<History> historySet = patient.getHistorySet();

            if (historySet == null) {
                return "There is no history for this patient!";
            }

            for (History h : historySet) {
                result.append("Visitation date: ").append(h.getVisitation().getDate()).append(System.lineSeparator())
                        .append("Visitation comments: ").append(h.getVisitation().getComments()).append(System.lineSeparator())
                        .append("Diagnose: ").append(h.getDiagnose().getName()).append(System.lineSeparator())
                        .append("Diagnose comments: ").append(h.getDiagnose().getComments()).append(System.lineSeparator())
                        .append("Prescribed Medicaments: ").append(h.getPrescribedMedicaments().getName())
                        .append(System.lineSeparator()).append(System.lineSeparator());
            }
        } else {
            result.append("Patient not found with ID: ").append(patientId);
        }

        return result.toString();

    }

    public void createPatient(Scanner scanner) {
        System.out.println("Enter patient first name:");
        String firstName = scanner.nextLine();

        System.out.println("Enter patient last name:");
        String lastName = scanner.nextLine();

        System.out.println("Enter patient address:");
        String address = scanner.nextLine();

        System.out.println("Enter patient email:");
        String email = scanner.nextLine();

        System.out.println("Enter patient birthdate (yyyy-MM-dd):");
        String birthdateStr = scanner.nextLine();
        LocalDate birthdate = LocalDate.parse(birthdateStr);

        Patient patient = new Patient(firstName, lastName, address, email, birthdate);

        System.out.println("Patient created successfully!");

        entityManager.getTransaction().begin();

        entityManager.persist(patient);

        entityManager.getTransaction().commit();
    }


    public void createHistory(Scanner scanner) {
        System.out.println("Enter patient ID: ");
        long patientId = Long.parseLong(scanner.nextLine());
        Patient patient = getPatientById(patientId);

        System.out.println("Enter diagnose name: ");
        String diagnoseName = scanner.nextLine();
        System.out.println("Enter diagnose comments(optional): ");
        String diagnoseComments = scanner.nextLine();

        Diagnose diagnose = new Diagnose(diagnoseName, diagnoseComments);


        System.out.println("Enter visitation date: ");
        LocalDate visitationDate = LocalDate.parse(scanner.nextLine());
        System.out.println("Enter visitation comments(optional): ");
        String visitationComments = scanner.nextLine();

        Visitation visitation = new Visitation(visitationDate, visitationComments);

        System.out.println("Enter prescribed medicaments: ");
        String medicaments = scanner.nextLine();

        PrescribedMedicaments prescribedMedicaments = new PrescribedMedicaments(medicaments);

        History history = new History(visitation, diagnose, prescribedMedicaments);

        entityManager.getTransaction().begin();
        patient.getHistorySet().add(history);
        entityManager.getTransaction().commit();

        System.out.println("Successfully created history for patient " + patient.getFirstName() + " " + patient.getLastName());
    }
}

