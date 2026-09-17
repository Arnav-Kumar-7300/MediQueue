import exception.MedicalRecordException;
import exception.QueueException;
import exception.InvalidAppointmentException;
import exception.DoctorNotFoundException;
import exception.PatientNotFoundException;

import model.MedicalRecord;
import model.QueueEntry;
import model.Appointment;
import model.Doctor;
import model.Patient;

import service.ReportService;
import service.MedicalRecordService;
import service.QueueService;
import service.AppointmentService;
import service.DoctorService;
import service.PatientService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import util.InputValidator;

import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final PatientService patientService =
            new PatientService();

    private static final DoctorService doctorService =
            new DoctorService();

    private static final AppointmentService appointmentService =
            new AppointmentService(
                    patientService,
                    doctorService
            );

    private static final QueueService queueService =
            new QueueService(
                    patientService,
                    appointmentService
            );

    private static final MedicalRecordService medicalRecordService =
            new MedicalRecordService(
                    patientService,
                    doctorService
            );

    private static final ReportService reportService =
            new ReportService(
                    patientService,
                    doctorService,
                    appointmentService,
                    queueService,
                    medicalRecordService
            );

    public static void main(String[] args) {

        boolean running = true;

        while (running) {

            displayMainMenu();

            int choice = readInteger("Enter your choice: ");

            switch (choice) {

                case 1:
                    patientManagementMenu();
                    break;

                case 2:
                    doctorManagementMenu();
                    break;

                case 3:
                    appointmentManagementMenu();
                    break;

                case 4:
                    queueManagementMenu();
                    break;

                case 5:
                    medicalRecordManagementMenu();
                    break;

                case 6:
                    reportsMenu();
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

    private static void doctorManagementMenu() {

        boolean running = true;

        while (running) {

            System.out.println("\n========== DOCTOR MANAGEMENT ==========");

            System.out.println("1. Add Doctor");
            System.out.println("2. View All Doctors");
            System.out.println("3. Search Doctor");
            System.out.println("4. Search by Specialization");
            System.out.println("5. Update Doctor");
            System.out.println("6. Change Availability");
            System.out.println("7. Delete Doctor");
            System.out.println("8. Back");

            int choice = readInteger("Enter your choice: ");

            switch (choice) {

                case 1:
                    addDoctor();
                    break;

                case 2:
                    viewAllDoctors();
                    break;

                case 3:
                    searchDoctor();
                    break;

                case 4:
                    searchDoctorsBySpecialization();
                    break;

                case 5:
                    updateDoctor();
                    break;

                case 6:
                    changeDoctorAvailability();
                    break;

                case 7:
                    deleteDoctor();
                    break;

                case 8:
                    running = false;
                    break;

                default:
                    System.out.println(
                        "Invalid choice. Please try again."
                );
            }
        }
    }

    private static void addDoctor() {

        System.out.println("\n---------- ADD DOCTOR ----------");

        String name;

        while (true) {

            System.out.print("Enter doctor name: ");
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

        String specialization;

        while (true) {

            System.out.print("Enter specialization: ");
            specialization = scanner.nextLine();

            if (!specialization.trim().isEmpty()) {
                break;
            }

            System.out.println(
                    "Specialization cannot be empty."
            );
        }

        String qualification;

         while (true) {

            System.out.print("Enter qualification: ");
            qualification = scanner.nextLine();

            if (!qualification.trim().isEmpty()) {
                break;
            }

            System.out.println(
                    "Qualification cannot be empty."
            );
        }

        Doctor doctor = doctorService.addDoctor(
                name,
                phone,
                email,
                specialization,
                qualification
        );

        System.out.println("\nDoctor added successfully!");
        System.out.println(
                "Generated Doctor ID: " + doctor.getId()
        );
    }

    private static void viewAllDoctors() {

        System.out.println("\n---------- ALL DOCTORS ----------");

        if (doctorService.getAllDoctors().isEmpty()) {

            System.out.println("No doctors registered yet.");
            return;
        }

        for (Doctor doctor : doctorService.getAllDoctors()) {

            doctor.displayDetails();

            System.out.println("----------------------------------");
        }

        System.out.println(
                "Total Doctors: "
                        + doctorService.getDoctorCount()
        );

        System.out.println(
                "Available Doctors: "
                        + doctorService.getAvailableDoctorCount()
        );
    }

    private static void searchDoctor() {

        System.out.println("\n---------- SEARCH DOCTOR ----------");

        System.out.print("Enter Doctor ID: ");
        String doctorId = scanner.nextLine();

        try {

            Doctor doctor =
                    doctorService.findDoctorById(doctorId);

            doctor.displayDetails();

        } catch (DoctorNotFoundException e) {

            System.out.println(
                    "ERROR: " + e.getMessage()
            );
        }
    }

    private static void searchDoctorsBySpecialization() {

        System.out.println(
                "\n---------- SEARCH BY SPECIALIZATION ----------"
        );

        System.out.print("Enter specialization: ");
        String specialization = scanner.nextLine();

        var doctors =
                doctorService.findDoctorsBySpecialization(
                        specialization
                );

        if (doctors.isEmpty()) {

            System.out.println(
                    "No doctors found for specialization: "
                            + specialization
            );

            return;
        }

        System.out.println(
                "\nDoctors specializing in "
                        + specialization + ":"
        );

        System.out.println();

        for (Doctor doctor : doctors) {

            doctor.displayDetails();

            System.out.println("----------------------------------");
        }
    }

    private static void updateDoctor() {

        System.out.println("\n---------- UPDATE DOCTOR ----------");

        System.out.print("Enter Doctor ID: ");
        String doctorId = scanner.nextLine();

        try {

            Doctor doctor =
                    doctorService.findDoctorById(doctorId);

            System.out.println(
                    "Updating doctor: "
                            + doctor.getName()
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

            String specialization;

            while (true) {

                System.out.print("Enter new specialization: ");
                specialization = scanner.nextLine();

                if (!specialization.trim().isEmpty()) {
                    break;
                }

                System.out.println(
                        "Specialization cannot be empty."
                );
            }

            String qualification;

            while (true) {

                System.out.print("Enter new qualification: ");
                qualification = scanner.nextLine();

                if (!qualification.trim().isEmpty()) {
                    break;
                }

                System.out.println(
                        "Qualification cannot be empty."
                );
            }

            doctorService.updateDoctor(
                    doctorId,
                    name,
                    phone,
                    email,
                    specialization,
                    qualification
            );

            System.out.println(
                    "\nDoctor updated successfully!"
            );

        } catch (DoctorNotFoundException e) {

            System.out.println(
                    "ERROR: " + e.getMessage()
            );
        }
    }

    private static void changeDoctorAvailability() {

        System.out.println(
                "\n---------- CHANGE AVAILABILITY ----------"
        );

        System.out.print("Enter Doctor ID: ");
        String doctorId = scanner.nextLine();

        try {

            Doctor doctor =
                    doctorService.findDoctorById(doctorId);

            System.out.println(
                    "Doctor: " + doctor.getName()
            );

            System.out.println(
                    "Current Availability: "
                            + (doctor.isAvailable()
                            ? "Available"
                            : "Unavailable")
            );

            System.out.println("\n1. Set Available");
            System.out.println("2. Set Unavailable");

            int choice =
                    readInteger("Enter choice: ");

            if (choice == 1) {

                doctorService.setDoctorAvailability(
                        doctorId,
                        true
                );

                System.out.println(
                        "Doctor is now AVAILABLE."
                );

            } else if (choice == 2) {

                doctorService.setDoctorAvailability(
                        doctorId,
                        false
                );

                System.out.println(
                        "Doctor is now UNAVAILABLE."
                );

            } else {

                System.out.println(
                        "Invalid choice."
                );
            }

        } catch (DoctorNotFoundException e) {

            System.out.println(
                    "ERROR: " + e.getMessage()
            );
        }
    }

    private static void deleteDoctor() {

        System.out.println(
                "\n---------- DELETE DOCTOR ----------"
        );

        System.out.print("Enter Doctor ID: ");
        String doctorId = scanner.nextLine();

        try {

            Doctor doctor =
                    doctorService.findDoctorById(doctorId);

            System.out.println(
                    "Doctor: " + doctor.getName()
            );

            System.out.print(
                    "Are you sure you want to delete this doctor? (yes/no): "
            );

            String confirmation = scanner.nextLine();

            if (confirmation.equalsIgnoreCase("yes")) {

                doctorService.deleteDoctor(doctorId);

                System.out.println(
                        "Doctor deleted successfully!"
                );

            } else {

                System.out.println(
                        "Deletion cancelled."
                );
            }

        } catch (DoctorNotFoundException e) {

            System.out.println(
                    "ERROR: " + e.getMessage()
            );
        }
    }

    private static void appointmentManagementMenu() {

        while (true) {

            System.out.println();
            System.out.println("=================================");
            System.out.println("      APPOINTMENT MANAGEMENT");
            System.out.println("=================================");
            System.out.println("1. Book Appointment");
            System.out.println("2. View All Appointments");
            System.out.println("3. Search Appointment");
            System.out.println("4. View Patient Appointments");
            System.out.println("5. View Doctor Appointments");
            System.out.println("6. Cancel Appointment");
            System.out.println("7. Reschedule Appointment");
            System.out.println("8. Complete Appointment");
            System.out.println("9. Back");
            System.out.println("=================================");

            int choice = readInteger(
                    "Enter your choice: "
            );

            switch (choice) {

                case 1:
                    bookAppointment();
                    break;

                case 2:
                    viewAllAppointments();
                    break;

                case 3:
                    searchAppointment();
                    break;

                case 4:
                    viewPatientAppointments();
                    break;

                case 5:
                    viewDoctorAppointments();
                    break;

                case 6:
                    cancelAppointment();
                    break;

                case 7:
                    rescheduleAppointment();
                    break;

                case 8:
                    completeAppointment();
                    break;

                case 9:
                    return;

                default:
                    System.out.println(
                            "Invalid choice. Please try again."
                    );
            }
        }
    }

    private static void bookAppointment() {

        System.out.println();
        System.out.println("---------- Book Appointment ----------");

        System.out.print("Enter Patient ID: ");
        String patientId = scanner.nextLine().trim();

        System.out.print("Enter Doctor ID: ");
        String doctorId = scanner.nextLine().trim();

        LocalDate date;

        while (true) {

            System.out.print(
                    "Enter appointment date (YYYY-MM-DD): "
            );

            String dateInput = scanner.nextLine().trim();

            try {

                date = LocalDate.parse(dateInput);
                break;

            } catch (DateTimeParseException e) {

                System.out.println(
                        "Invalid date format. Use YYYY-MM-DD."
                );
            }
        }

        LocalTime time;

        while (true) {

            System.out.print(
                    "Enter appointment time (HH:MM): "
            );

            String timeInput = scanner.nextLine().trim();

            try {

                time = LocalTime.parse(timeInput);
                break;

            } catch (DateTimeParseException e) {

                System.out.println(
                        "Invalid time format. Use HH:MM."
                );
            }
        }

        try {

            Appointment appointment =
                    appointmentService.bookAppointment(
                            patientId,
                            doctorId,
                            date,
                            time
                    );

            System.out.println();
            System.out.println(
                    "Appointment booked successfully!"
            );

            System.out.println(
                    "Appointment ID: "
                            + appointment.getAppointmentId()
            );

        } catch (Exception e) {

            System.out.println(
                    "Error: " + e.getMessage()
            );
        }
    }

    private static void viewAllAppointments() {

        System.out.println();
        System.out.println("---------- All Appointments ----------");

        var appointments =
                appointmentService.getAllAppointments();

        if (appointments.isEmpty()) {

            System.out.println(
                     "No appointments found."
            );

            return;
        }

        for (Appointment appointment : appointments) {

            System.out.println();
            appointment.displayDetails();

            System.out.println("---------------------------------");
        }

        System.out.println(
                "Total Appointments: "
                        + appointmentService.getAppointmentCount()
        );
    }

    private static void searchAppointment() {

        System.out.println();
        System.out.println("---------- Search Appointment ----------");

        System.out.print("Enter Appointment ID: ");
        String appointmentId =
                scanner.nextLine().trim();

        try {

            Appointment appointment =
                    appointmentService.findAppointmentById(
                            appointmentId
                    );

            System.out.println();
            appointment.displayDetails();

        } catch (InvalidAppointmentException e) {

            System.out.println(
                    "Error: " + e.getMessage()
            );
        }
    }

    private static void viewPatientAppointments() {

        System.out.println();
        System.out.println(
                "---------- Patient Appointments ----------"
        );

        System.out.print("Enter Patient ID: ");
        String patientId =
                scanner.nextLine().trim();

        try {

            var appointments =
                    appointmentService.getPatientAppointments(
                            patientId
                    );

            if (appointments.isEmpty()) {

                System.out.println(
                        "No appointments found for this patient."
                );

                return;
            }

            for (Appointment appointment : appointments) {

                System.out.println();
                appointment.displayDetails();
                System.out.println("---------------------------------");
            }

        } catch (Exception e) {

            System.out.println(
                    "Error: " + e.getMessage()
            );
        }
    }

    private static void viewDoctorAppointments() {

        System.out.println();
        System.out.println(
                "---------- Doctor Appointments ----------"
        );

        System.out.print("Enter Doctor ID: ");
        String doctorId =
                scanner.nextLine().trim();

        try {

            var appointments =
                    appointmentService.getDoctorAppointments(
                            doctorId
                    );

            if (appointments.isEmpty()) {

                System.out.println(
                        "No appointments found for this doctor."
                );

                return;
            }

            for (Appointment appointment : appointments) {

                System.out.println();
                appointment.displayDetails();
                System.out.println("---------------------------------");
            }

        } catch (Exception e) {

            System.out.println(
                    "Error: " + e.getMessage()
            );
        }
    }

    private static void cancelAppointment() {

        System.out.println();
        System.out.println(
                "---------- Cancel Appointment ----------"
        );

        System.out.print("Enter Appointment ID: ");
        String appointmentId =
                scanner.nextLine().trim();

        try {

            appointmentService.cancelAppointment(
                    appointmentId
            );

            System.out.println(
                    "Appointment cancelled successfully."
            );

        } catch (InvalidAppointmentException e) {

            System.out.println(
                    "Error: " + e.getMessage()
            );
        }
    }

    private static void rescheduleAppointment() {

        System.out.println();
        System.out.println(
                "---------- Reschedule Appointment ----------"
        );

        System.out.print("Enter Appointment ID: ");
        String appointmentId =
                scanner.nextLine().trim();

        LocalDate newDate;

        while (true) {

            System.out.print(
                    "Enter new date (YYYY-MM-DD): "
            );

            String input = scanner.nextLine().trim();

            try {

                newDate = LocalDate.parse(input);
                break;

            } catch (DateTimeParseException e) {

                System.out.println(
                        "Invalid date format."
                );
            }
        }

        LocalTime newTime;

        while (true) {

            System.out.print(
                    "Enter new time (HH:MM): "
            );

            String input = scanner.nextLine().trim();

            try {

                newTime = LocalTime.parse(input);
                break;

            } catch (DateTimeParseException e) {

                System.out.println(
                        "Invalid time format."
                );
            }
        }

        try {

            appointmentService.rescheduleAppointment(
                    appointmentId,
                    newDate,
                    newTime
            );

            System.out.println(
                    "Appointment rescheduled successfully."
            );

        } catch (InvalidAppointmentException e) {

            System.out.println(
                    "Error: " + e.getMessage()
            );
        }
    }

    private static void completeAppointment() {

        System.out.println();
        System.out.println(
                "---------- Complete Appointment ----------"
        );

        System.out.print("Enter Appointment ID: ");
        String appointmentId =
                scanner.nextLine().trim();

        try {

            appointmentService.completeAppointment(
                    appointmentId
            );

            System.out.println(
                    "Appointment marked as completed."
            );

        } catch (InvalidAppointmentException e) {

            System.out.println(
                    "Error: " + e.getMessage()
            );
        }
    }

    private static void queueManagementMenu() {

        while (true) {

            System.out.println();
            System.out.println("=================================");
            System.out.println("        QUEUE MANAGEMENT");
            System.out.println("=================================");
            System.out.println("1. Add Patient to Queue");
            System.out.println("2. View Current Queue");
            System.out.println("3. Search Queue Entry");
            System.out.println("4. Call Next Patient");
            System.out.println("5. Remove Patient from Queue");
            System.out.println("6. Queue Statistics");
            System.out.println("7. Back");
            System.out.println("=================================");

            int choice =
                    readInteger("Enter your choice: ");

            switch (choice) {

                case 1:
                    addPatientToQueue();
                    break;

                case 2:
                    viewCurrentQueue();
                    break;

                case 3:
                    searchQueueEntry();
                    break;

                case 4:
                    callNextPatient();
                    break;

                case 5:
                    removePatientFromQueue();
                    break;

                case 6:
                    showQueueStatistics();
                    break;

                case 7:
                    return;

                default:
                    System.out.println(
                            "Invalid choice. Please try again."
                    );
            }
        }
    }

    private static void addPatientToQueue() {

        System.out.println();
        System.out.println(
                "---------- Add Patient to Queue ----------"
        );

        System.out.print("Enter Patient ID: ");
        String patientId =
                scanner.nextLine().trim();

        System.out.print(
                "Enter Appointment ID (press Enter for walk-in): "
        );

        String appointmentId =
                scanner.nextLine().trim();

        System.out.println();
        System.out.println("Priority Levels:");
        System.out.println("1. Emergency");
        System.out.println("2. Urgent");
        System.out.println("3. Regular");

        int priority =
                readInteger("Enter priority: ");

        if (appointmentId.isEmpty()) {
            appointmentId = null;
        }

        try {

            QueueEntry entry =
                    queueService.addToQueue(
                            patientId,
                            appointmentId,
                            priority
                    );

            System.out.println();
            System.out.println(
                    "Patient added to queue successfully!"
            );

            System.out.println(
                    "Queue ID: "
                            + entry.getQueueId()
            );

            System.out.println(
                    "Priority: "
                            + entry.getPriorityLabel()
            );

        } catch (QueueException e) {

            System.out.println(
                    "Error: " + e.getMessage()
            );
        }
    }

    private static void viewCurrentQueue() {

        System.out.println();
        System.out.println(
                "---------- CURRENT QUEUE ----------"
        );

        var queue =
                queueService.getCurrentQueue();

        if (queue.isEmpty()) {

            System.out.println(
                    "No patients are currently waiting."
            );

            return;
        }

        int position = 1;

        for (QueueEntry entry : queue) {

            System.out.println();
            System.out.println(
                    "Queue Position: " + position
            );

            entry.displayDetails();

            System.out.println(
                    "---------------------------------"
            );

            position++;
        }

        System.out.println(
                "Patients Waiting: "
                        + queueService.getWaitingCount()
        );
    }

    private static void searchQueueEntry() {

        System.out.println();
        System.out.println(
                "---------- SEARCH QUEUE ENTRY ----------"
        );

        System.out.print("Enter Queue ID: ");
        String queueId =
                scanner.nextLine().trim();

        try {

            QueueEntry entry =
                    queueService.findQueueEntry(
                            queueId
                    );

            System.out.println();
            entry.displayDetails();

        } catch (QueueException e) {

            System.out.println(
                    "Error: " + e.getMessage()
            );
        }
    }

    private static void callNextPatient() {

        System.out.println();
        System.out.println(
                "---------- CALL NEXT PATIENT ----------"
        );

        try {

            QueueEntry entry =
                    queueService.callNextPatient();

            System.out.println();
            System.out.println(
                    "Now serving:"
            );

            entry.displayDetails();

        } catch (QueueException e) {

            System.out.println(
                    "Error: " + e.getMessage()
            );
        }
    }

    private static void removePatientFromQueue() {

        System.out.println();
        System.out.println(
                "---------- REMOVE FROM QUEUE ----------"
        );

        System.out.print("Enter Queue ID: ");
        String queueId =
                scanner.nextLine().trim();

        try {

            queueService.removeFromQueue(
                    queueId
            );

            System.out.println(
                    "Patient removed from queue successfully."
            );

        } catch (QueueException e) {

            System.out.println(
                    "Error: " + e.getMessage()
            );
        }
    }

    private static void showQueueStatistics() {

        System.out.println();
        System.out.println(
                "---------- QUEUE STATISTICS ----------"
        );

        System.out.println(
                "Currently Waiting : "
                        + queueService.getWaitingCount()
        );

        System.out.println(
                "Patients Served   : "
                        + queueService.getServedCount()
        );

        System.out.println(
                "Patients Removed  : "
                        + queueService.getRemovedCount()
        );

        System.out.println(
                "Total Queue Entries: "
                        + queueService.getTotalEntries()
        );
    }

    private static void medicalRecordManagementMenu() {

        while (true) {

            System.out.println();
            System.out.println("=================================");
            System.out.println("      MEDICAL RECORDS");
            System.out.println("=================================");
            System.out.println("1. Add Medical Record");
            System.out.println("2. View All Medical Records");
            System.out.println("3. Search Medical Record");
            System.out.println("4. View Patient Medical History");
            System.out.println("5. View Doctor Records");
            System.out.println("6. Delete Medical Record");
            System.out.println("7. Back");
            System.out.println("=================================");

            int choice =
                    readInteger("Enter your choice: ");

            switch (choice) {

                case 1:
                    addMedicalRecord();
                    break;

                case 2:
                    viewAllMedicalRecords();
                    break;

                case 3:
                    searchMedicalRecord();
                    break;

                case 4:
                    viewPatientMedicalHistory();
                    break;

                case 5:
                    viewDoctorMedicalRecords();
                    break;

                case 6:
                    deleteMedicalRecord();
                    break;

                case 7:
                    return;

                default:
                    System.out.println(
                            "Invalid choice. Please try again."
                    );
            }
        }
    }

    private static void addMedicalRecord() {

        System.out.println();
        System.out.println(
                "---------- ADD MEDICAL RECORD ----------"
        );

        try {
            System.out.print("Enter Patient ID: ");
            String patientId = scanner.nextLine().trim();

            Patient patient = patientService.findPatientById(patientId);

            System.out.println("Patient found: " + patient.getName());

            System.out.print("Enter Doctor ID: ");
            String doctorId = scanner.nextLine().trim();

            Doctor doctor = doctorService.findDoctorById(doctorId);

            System.out.println("Doctor found: " + doctor.getName());

            System.out.print("Enter Record Date (YYYY-MM-DD): ");
            LocalDate date = LocalDate.parse(scanner.nextLine().trim());

            System.out.print("Enter Diagnosis: ");
            String diagnosis = scanner.nextLine().trim();

            System.out.print("Enter Prescription: ");
            String prescription = scanner.nextLine().trim();

            System.out.print("Enter Notes: ");
            String notes = scanner.nextLine().trim();

            MedicalRecord record = medicalRecordService.addMedicalRecord(
                    patientId,
                    doctorId,
                    date,
                    diagnosis,
                    prescription,
                    notes
            );

            System.out.println("\nMedical record added successfully!");
            System.out.println("Record ID: " + record.getRecordId());

        } catch (PatientNotFoundException e) {

            System.out.println("\nError: " + e.getMessage());

        } catch (DoctorNotFoundException e) {

            System.out.println("\nError: " + e.getMessage());

        } catch (Exception e) {

            System.out.println("\nError: " + e.getMessage());
        }
    }

    private static void viewAllMedicalRecords() {

        System.out.println();
        System.out.println(
                "---------- ALL MEDICAL RECORDS ----------"
        );

        var records =
                medicalRecordService
                        .getAllMedicalRecords();

        if (records.isEmpty()) {

            System.out.println(
                    "No medical records found."
            );

            return;
        }

        for (MedicalRecord record : records) {

            System.out.println();

            record.displayDetails();

            System.out.println(
                    "---------------------------------"
            );
        }

        System.out.println(
                "Total Medical Records: "
                        + medicalRecordService
                        .getMedicalRecordCount()
        );
    }

    private static void searchMedicalRecord() {

        System.out.println();
        System.out.println(
                "---------- SEARCH MEDICAL RECORD ----------"
        );

        System.out.print("Enter Record ID: ");
        String recordId =
                scanner.nextLine().trim();

        try {

            MedicalRecord record =
                    medicalRecordService
                            .findMedicalRecordById(
                                    recordId
                            );

            System.out.println();

            record.displayDetails();

        } catch (MedicalRecordException e) {

            System.out.println(
                    "Error: " + e.getMessage()
            );
        }
    }

    private static void viewPatientMedicalHistory() {

        System.out.println();
        System.out.println(
                "---------- PATIENT MEDICAL HISTORY ----------"
        );

        System.out.print("Enter Patient ID: ");
        String patientId =
                scanner.nextLine().trim();

        try {

            var records =
                    medicalRecordService
                            .getPatientMedicalHistory(
                                    patientId
                            );

            if (records.isEmpty()) {

                System.out.println(
                        "No medical history found for this patient."
                );

                return;
            }

            System.out.println(
                    "Medical History for Patient: "
                            + patientId
            );

            for (MedicalRecord record : records) {

                System.out.println();

                record.displayDetails();

                System.out.println(
                        "---------------------------------"
                );
            }

        } catch (PatientNotFoundException e) {

            System.out.println(
                    "Error: " + e.getMessage()
            );
        }
    }

    private static void viewDoctorMedicalRecords() {

        System.out.println();
        System.out.println(
                "---------- DOCTOR MEDICAL RECORDS ----------"
        );

        System.out.print("Enter Doctor ID: ");
        String doctorId =
                scanner.nextLine().trim();

        try {

            var records =
                    medicalRecordService
                            .getDoctorMedicalRecords(
                                        doctorId
                            );

            if (records.isEmpty()) {

                System.out.println(
                        "No medical records found for this doctor."
                );

                return;
            }

            for (MedicalRecord record : records) {

                System.out.println();

                record.displayDetails();

                System.out.println(
                        "---------------------------------"
                );
            }

        } catch (DoctorNotFoundException e) {

            System.out.println(
                    "Error: " + e.getMessage()
            );
        }
    }

    private static void deleteMedicalRecord() {

        System.out.println();
        System.out.println(
                "---------- DELETE MEDICAL RECORD ----------"
        );

        System.out.print("Enter Record ID: ");
        String recordId =
                scanner.nextLine().trim();

        System.out.print(
                "Are you sure you want to delete this record? (yes/no): "
        );

        String confirmation =
                scanner.nextLine().trim();

        if (!confirmation.equalsIgnoreCase("yes")) {

            System.out.println(
                    "Delete operation cancelled."
            );

            return;
        }

        try {

            medicalRecordService
                    .deleteMedicalRecord(recordId);

            System.out.println(
                    "Medical record deleted successfully."
            );

        } catch (MedicalRecordException e) {

            System.out.println(
                    "Error: " + e.getMessage()
            );
        }
    }

    private static void reportsMenu() {

        while (true) {

            System.out.println();
            System.out.println("=================================");
            System.out.println("       REPORTS & STATISTICS");
            System.out.println("=================================");
            System.out.println("1. Hospital Dashboard");
            System.out.println("2. Today's Appointments");
            System.out.println("3. Appointment Status Report");
            System.out.println("4. Doctor-wise Appointment Report");
            System.out.println("5. Patient-wise Appointment Report");
            System.out.println("6. Queue Report");
            System.out.println("7. Medical Record Report");
            System.out.println("8. Back");
            System.out.println("=================================");

            int choice =
                    readInteger("Enter your choice: ");

            switch (choice) {

                case 1:
                    showDashboard();
                    break;

                case 2:
                    showTodaysAppointments();
                    break;

                case 3:
                    showAppointmentStatusReport();
                    break;

                case 4:
                    showDoctorWiseReport();
                    break;

                case 5:
                    showPatientWiseReport();
                    break;

                case 6:
                    showQueueReport();
                    break;

                case 7:
                    showMedicalRecordReport();
                    break;

                case 8:
                    return;

                default:
                    System.out.println(
                            "Invalid choice. Please try again."
                    );
            }
        }
    }

    private static void showDashboard() {

        reportService.displayDashboard();
    }

    private static void showTodaysAppointments() {

        System.out.println();
        System.out.println(
                "---------- TODAY'S APPOINTMENTS ----------"
        );

        var appointments =
                reportService.getTodaysAppointments();

        if (appointments.isEmpty()) {

            System.out.println(
                    "No appointments scheduled for today."
            );

            return;
        }

        for (Appointment appointment :
                appointments) {

            System.out.println();

            appointment.displayDetails();

            System.out.println(
                    "---------------------------------"
            );
        }

        System.out.println(
                "Today's Appointments: "
                        + appointments.size()
        );
    }

    private static void showAppointmentStatusReport() {

        System.out.println();
        System.out.println(
                "---------- APPOINTMENT STATUS REPORT ----------"
        );

        System.out.println(
                "Total Appointments : "
                        + reportService
                        .getTotalAppointments()
        );

        System.out.println(
                "Scheduled          : "
                        + reportService
                        .getScheduledAppointments()
        );

        System.out.println(
                "Completed          : "
                        + reportService
                        .getCompletedAppointments()
        );

        System.out.println(
                "Cancelled          : "
                        + reportService
                        .getCancelledAppointments()
        );

        System.out.println(
                "Rescheduled        : "
                        + reportService
                        .getRescheduledAppointments()
        );
    }

    private static void showDoctorWiseReport() {

        System.out.println();
        System.out.println(
                "---------- DOCTOR-WISE APPOINTMENTS ----------"
        );

        var report =
                reportService
                        .getDoctorWiseAppointmentCount();

        if (report.isEmpty()) {

            System.out.println(
                    "No appointment data available."
            );

            return;
        }

        for (var entry : report.entrySet()) {

            System.out.println(
                    "Doctor ID: "
                            + entry.getKey()
                            + " | Appointments: "
                            + entry.getValue()
            );
        }
    }

    private static void showPatientWiseReport() {

        System.out.println();
        System.out.println(
                "---------- PATIENT-WISE APPOINTMENTS ----------"
        );

        var report =
                reportService
                        .getPatientWiseAppointmentCount();

        if (report.isEmpty()) {

            System.out.println(
                    "No appointment data available."
            );

            return;
        }

        for (var entry : report.entrySet()) {

            System.out.println(
                    "Patient ID: "
                            + entry.getKey()
                            + " | Appointments: "
                            + entry.getValue()
            );
        }
    }

    private static void showQueueReport() {

        System.out.println();
        System.out.println(
                "---------- QUEUE REPORT ----------"
        );

        System.out.println(
                "Currently Waiting : "
                        + reportService
                        .getWaitingPatients()
        );

        System.out.println(
                "Patients Served   : "
                        + reportService
                        .getServedPatients()
        );

        System.out.println(
                "Patients Removed  : "
                        + reportService
                        .getRemovedPatients()
        );

        System.out.println(
                "Total Queue Entries: "
                        + reportService
                        .getTotalQueueEntries()
        );
    }

    private static void showMedicalRecordReport() {

        System.out.println();
        System.out.println(
                "---------- MEDICAL RECORD REPORT ----------"
        );

        System.out.println(
                "Total Medical Records: "
                        + reportService
                        .getTotalMedicalRecords()
        );
    }
}