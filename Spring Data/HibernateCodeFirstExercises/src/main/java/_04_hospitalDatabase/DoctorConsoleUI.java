package _04_hospitalDatabase;

import java.util.Scanner;

public class DoctorConsoleUI {
    private Scanner scanner;
    private HospitalDatabaseManager databaseManager;

    public DoctorConsoleUI() {
        scanner = new Scanner(System.in);
        databaseManager = new HospitalDatabaseManager(); // Initialize your database manager or service class
    }

    public void run() {
        boolean running = true;

        while (running) {
            displayMenu();

            int choice = getUserChoice();



            switch (choice) {
                case 1:
                    databaseManager.printPatientInformation(scanner);
                    break;
                case 2:
                    databaseManager.createPatient(scanner);
                    break;
                case 3:
                    System.out.println(databaseManager.getPatientHistory(scanner));
                    break;
                case 4: databaseManager.createHistory(scanner);
                    break;
                case 5:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        System.out.println("Exiting the application. Goodbye!");
    }

    private void displayMenu() {
        System.out.println("========== Doctor Console Menu ==========");
        System.out.println("1. View Patient Information");
        System.out.println("2. Create Patient");
        System.out.println("3. View Patient History");
        System.out.println("4. Create new History");
        System.out.println("5. Exit");
        System.out.println("========================================");
    }

    private int getUserChoice() {
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        return choice;
    }

    // Add additional methods for handling each menu option

    public static void main(String[] args) {
        DoctorConsoleUI doctorConsoleUI = new DoctorConsoleUI();
        doctorConsoleUI.run();
    }
}
