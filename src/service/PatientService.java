package service;

import exception.PatientNotFoundException;
import model.Patient;
import util.IDGenerator;

import java.util.ArrayList;
import java.util.List;

import util.FileManager;

import java.io.IOException;

public class PatientService {

    private static final String PATIENT_FILE =
            "data/patients.txt";

    private final List<Patient> patients;

    public PatientService() {

        patients = new ArrayList<>();

        loadPatients();
    }

    // Register a new patient
    public Patient registerPatient(
            String name,
            String phone,
            String email,
            int age,
            String gender,
            String bloodGroup) {

        String patientId = IDGenerator.generatePatientId();

        Patient patient = new Patient(
                patientId,
                name,
                phone,
                email,
                age,
                gender,
                bloodGroup
        );

        patients.add(patient);

        savePatients();

        return patient;
    }

    // Return all patients
    public List<Patient> getAllPatients() {

        return patients;
    }

    // Find patient by ID
    public Patient findPatientById(String patientId)
            throws PatientNotFoundException {

        for (Patient patient : patients) {

            if (patient.getId().equalsIgnoreCase(patientId)) {
                return patient;
            }
        }

        throw new PatientNotFoundException(
                "Patient with ID " + patientId + " was not found."
        );
    }

    // Update patient information
    public void updatePatient(
            String patientId,
            String name,
            String phone,
            String email,
            int age,
            String gender,
            String bloodGroup)
            throws PatientNotFoundException {

        Patient patient = findPatientById(patientId);

        patient.setName(name);
        patient.setPhone(phone);
        patient.setEmail(email);
        patient.setAge(age);
        patient.setGender(gender);
        patient.setBloodGroup(bloodGroup);

        savePatients();
    }

    // Delete patient
    public void deletePatient(String patientId)
            throws PatientNotFoundException {

        Patient patient = findPatientById(patientId);

        patients.remove(patient);

        savePatients();
    }

    // Count total patients
    public int getPatientCount() {

        return patients.size();
    }

    private void savePatients() {

        List<String> lines =
                new ArrayList<>();

        for (Patient patient : patients) {

            String line =
                    patient.getId() + "|" +
                    patient.getName() + "|" +
                    patient.getPhone() + "|" +
                    patient.getEmail() + "|" +
                    patient.getAge() + "|" +
                    patient.getGender() + "|" +
                    patient.getBloodGroup();

            lines.add(line);
        }

        try {

            FileManager.writeToFile(
                    PATIENT_FILE,
                    lines
            );

        } catch (IOException e) {

            System.out.println(
                    "Warning: Unable to save patient data."
            );
        }
    }

    private void loadPatients() {

        try {

            List<String> lines =
                    FileManager.readFromFile(
                            PATIENT_FILE
                    );

            for (String line : lines) {

                String[] data =
                        line.split("\\|", -1);

                if (data.length != 7) {
                    continue;
                }

                Patient patient =
                        new Patient(
                                data[0],
                                data[1],
                                data[2],
                                data[3],
                                Integer.parseInt(data[4]),
                                data[5],
                                data[6]
                        );

                patients.add(patient);
            }

        } catch (Exception e) {

            System.out.println(
                    "Warning: Unable to load patient data."
            );
        }
    }
}