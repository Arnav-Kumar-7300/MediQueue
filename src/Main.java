import exception.PatientNotFoundException;
import model.Patient;
import service.PatientService;
import util.InputValidator;

import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final PatientService patientService =
            new PatientService();

    public static void main(String[] args) {

        boolean running = true;

        while (running) {

            displayMainMenu();

            int choice = readInteger("Enter your choice: ");

            switch (choice) {

                case 1:
                    patientManagementMenu();
                    break;

                case 7:
                    running = false;
                    System.out.println("\nThank you for using MediQueue!");
                    break;

                default:
                    System.out.println(
                            "\nInvalid choice. Please try again."
                    );
            }
        }

        scanner.close();
    }

    private static void displayMainMenu() {

        System.out.println("\n========================================");
        System.out.println("              MEDIQUEUE");
        System.out.println(" Hospital Appointment & Queue System");
        System.out.println("========================================");

        System.out.println("1. Patient Management");
        System.out.println("2. Doctor Management");
        System.out.println("3. Appointment Management");
        System.out.println("4. Queue Management");
        System.out.println("5. Medical Records");
        System.out.println("6. Reports");
        System.out.println("7. Exit");

        System.out.println("========================================");
    }

    private static void patientManagementMenu() {

        boolean running = true;

        while (running) {

            System.out.println("\n========== PATIENT MANAGEMENT ==========");

            System.out.println("1. Register Patient");
            System.out.println("2. View All Patients");
            System.out.println("3. Search Patient");
            System.out.println("4. Update Patient");
            System.out.println("5. Delete Patient");
            System.out.println("6. Back");

            int choice = readInteger("Enter your choice: ");

            switch (choice) {

                case 1:
                    registerPatient();
                    break;

                case 2:
                    viewAllPatients();
                    break;

                case 3:
                    searchPatient();
                    break;

                case 4:
                    updatePatient();
                    break;

                case 5:
                    deletePatient();
                    break;

                case 6:
                    running = false;
                    break;

                default:
                    System.out.println(
                            "Invalid choice. Please try again."
                    );
            }
        }
    }

    private static void registerPatient() {

        System.out.println("\n---------- REGISTER PATIENT ----------");

        String name;

        while (true) {

            System.out.print("Enter name: ");
            name = scanner.nextLine();

            if (InputValidator.isValidName(name)) {
                break;
            }

            System.out.println(
                    "Invalid name. Use alphabets and spaces only."
            );
        }

        String phone;

        while (true) {

            System.out.print("Enter phone number: ");
            phone = scanner.nextLine();

            if (InputValidator.isValidPhone(phone)) {
                break;
            }

            System.out.println(
                    "Invalid phone number. Enter exactly 10 digits."
            );
        }

        String email;

        while (true) {

            System.out.print("Enter email: ");
            email = scanner.nextLine();

            if (InputValidator.isValidEmail(email)) {
                break;
            }

            System.out.println("Invalid email address.");
        }

        int age;

        while (true) {

            age = readInteger("Enter age: ");

            if (InputValidator.isValidAge(age)) {
                break;
            }

            System.out.println(
                    "Invalid age. Enter an age between 1 and 120."
            );
        }

        String gender;

        while (true) {

            System.out.print("Enter gender (Male/Female/Other): ");
            gender = scanner.nextLine();

            if (InputValidator.isValidGender(gender)) {
                break;
            }

            System.out.println(
                    "Invalid gender. Please enter Male, Female or Other."
            );
        }

        String bloodGroup;

        while (true) {

            System.out.print("Enter blood group: ");
            bloodGroup = scanner.nextLine();

            if (InputValidator.isValidBloodGroup(bloodGroup)) {
                break;
            }

            System.out.println(
                    "Invalid blood group."
            );
        }

        Patient patient = patientService.registerPatient(
                name,
                phone,
                email,
                age,
                gender,
                bloodGroup
        );

        System.out.println("\nPatient registered successfully!");
        System.out.println("Generated Patient ID: "
                + patient.getId());
    }

    private static void viewAllPatients() {

        System.out.println("\n---------- ALL PATIENTS ----------");

        if (patientService.getAllPatients().isEmpty()) {

            System.out.println("No patients registered yet.");
            return;
        }

        for (Patient patient : patientService.getAllPatients()) {

            patient.displayDetails();

            System.out.println("----------------------------------");
        }

        System.out.println(
                "Total Patients: "
                        + patientService.getPatientCount()
        );
    }

    private static void searchPatient() {

        System.out.println("\n---------- SEARCH PATIENT ----------");

        System.out.print("Enter Patient ID: ");
        String patientId = scanner.nextLine();

        try {

            Patient patient =
                    patientService.findPatientById(patientId);

            patient.displayDetails();

        } catch (PatientNotFoundException e) {

            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private static void updatePatient() {

        System.out.println("\n---------- UPDATE PATIENT ----------");

        System.out.print("Enter Patient ID: ");
        String patientId = scanner.nextLine();

        try {

            Patient patient =
                    patientService.findPatientById(patientId);

            System.out.println(
                    "Updating patient: "
                            + patient.getName()
            );

            String name;

            while (true) {

                System.out.print("Enter new name: ");
                name = scanner.nextLine();

                if (InputValidator.isValidName(name)) {
                    break;
                }

                System.out.println("Invalid name.");
            }

            String phone;

            while (true) {

                System.out.print("Enter new phone: ");
                phone = scanner.nextLine();

                if (InputValidator.isValidPhone(phone)) {
                    break;
                }

                System.out.println(
                        "Phone must contain exactly 10 digits."
                );
            }

            String email;

            while (true) {

                System.out.print("Enter new email: ");
                email = scanner.nextLine();

                if (InputValidator.isValidEmail(email)) {
                    break;
                }

                System.out.println("Invalid email.");
            }

            int age;

            while (true) {

                age = readInteger("Enter new age: ");

                if (InputValidator.isValidAge(age)) {
                    break;
                }

                System.out.println("Invalid age.");
            }

            String gender;

            while (true) {

                System.out.print(
                        "Enter new gender (Male/Female/Other): "
                );

                gender = scanner.nextLine();

                if (InputValidator.isValidGender(gender)) {
                    break;
                }

                System.out.println("Invalid gender.");
            }

            String bloodGroup;

            while (true) {

                System.out.print("Enter new blood group: ");
                bloodGroup = scanner.nextLine();

                if (InputValidator.isValidBloodGroup(bloodGroup)) {
                    break;
                }

                System.out.println("Invalid blood group.");
            }

            patientService.updatePatient(
                    patientId,
                    name,
                    phone,
                    email,
                    age,
                    gender,
                    bloodGroup
            );

            System.out.println(
                    "\nPatient updated successfully!"
            );

        } catch (PatientNotFoundException e) {

            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private static void deletePatient() {

        System.out.println("\n---------- DELETE PATIENT ----------");

        System.out.print("Enter Patient ID: ");
        String patientId = scanner.nextLine();

        try {

            Patient patient =
                    patientService.findPatientById(patientId);

            System.out.println(
                    "Patient: " + patient.getName()
            );

            System.out.print(
                    "Are you sure you want to delete this patient? (yes/no): "
            );

            String confirmation = scanner.nextLine();

            if (confirmation.equalsIgnoreCase("yes")) {

                patientService.deletePatient(patientId);

                System.out.println(
                        "Patient deleted successfully!"
                );

            } else {

                System.out.println("Deletion cancelled.");
            }

        } catch (PatientNotFoundException e) {

            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private static int readInteger(String message) {

        while (true) {

            try {

                System.out.print(message);

                return Integer.parseInt(
                        scanner.nextLine()
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "Invalid input. Please enter a number."
                );
            }
        }
    }
}