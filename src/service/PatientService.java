package service;

import exception.PatientNotFoundException;
import model.Patient;
import util.IDGenerator;

import java.util.ArrayList;
import java.util.List;

public class PatientService {

    private final List<Patient> patients;

    public PatientService() {
        patients = new ArrayList<>();
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
    }

    // Delete patient
    public void deletePatient(String patientId)
            throws PatientNotFoundException {

        Patient patient = findPatientById(patientId);

        patients.remove(patient);
    }

    // Count total patients
    public int getPatientCount() {

        return patients.size();
    }
}