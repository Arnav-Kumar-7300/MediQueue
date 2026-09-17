package service;

import exception.DoctorNotFoundException;
import exception.MedicalRecordException;
import exception.PatientNotFoundException;
import model.Doctor;
import model.MedicalRecord;
import util.IDGenerator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import util.FileManager;

import java.io.IOException;

public class MedicalRecordService {

    private static final String RECORD_FILE =
        "data/medical_records.txt";

    private final List<MedicalRecord> medicalRecords;
    private final PatientService patientService;
    private final DoctorService doctorService;

    public MedicalRecordService(
            PatientService patientService,
            DoctorService doctorService) {

        medicalRecords = new ArrayList<>();

        this.patientService = patientService;
        this.doctorService = doctorService;

        loadMedicalRecords();
    }

    // Add a new medical record
    public MedicalRecord addMedicalRecord(
            String patientId,
            String doctorId,
            LocalDate date,
            String diagnosis,
            String prescription,
            String notes)
            throws PatientNotFoundException,
            DoctorNotFoundException,
            MedicalRecordException {

        // Verify patient
        patientService.findPatientById(patientId);

        // Verify doctor
        doctorService.findDoctorById(doctorId);

        // Validate date
        if (date.isAfter(LocalDate.now())) {

            throw new MedicalRecordException(
                    "Medical record date cannot be in the future."
            );
        }

        // Validate text fields
        if (diagnosis == null
                || diagnosis.trim().isEmpty()) {

            throw new MedicalRecordException(
                    "Diagnosis cannot be empty."
            );
        }

        if (prescription == null
                || prescription.trim().isEmpty()) {

            throw new MedicalRecordException(
                    "Prescription cannot be empty."
            );
        }

        if (notes == null
                || notes.trim().isEmpty()) {

            throw new MedicalRecordException(
                    "Notes cannot be empty."
            );
        }

        String recordId =
                IDGenerator.generateRecordId();

        MedicalRecord record =
                new MedicalRecord(
                        recordId,
                        patientId,
                        doctorId,
                        date,
                        diagnosis,
                        prescription,
                        notes
                );

        medicalRecords.add(record);

        saveMedicalRecords();

        return record;
    }

    // Get all medical records
    public List<MedicalRecord> getAllMedicalRecords() {

        List<MedicalRecord> result =
                new ArrayList<>(medicalRecords);

        result.sort(
                Comparator.comparing(
                        MedicalRecord::getDate
                ).reversed()
        );

        return result;
    }

    // Find medical record by ID
    public MedicalRecord findMedicalRecordById(
            String recordId)
            throws MedicalRecordException {

        for (MedicalRecord record :
                medicalRecords) {

            if (record.getRecordId()
                    .equalsIgnoreCase(recordId)) {

                return record;
            }
        }

        throw new MedicalRecordException(
                "Medical record with ID "
                        + recordId
                        + " was not found."
        );
    }

    // Get medical history of a patient
    public List<MedicalRecord> getPatientMedicalHistory(
            String patientId)
            throws PatientNotFoundException {

        // Verify patient
        patientService.findPatientById(patientId);

        List<MedicalRecord> result =
                new ArrayList<>();

        for (MedicalRecord record :
                medicalRecords) {

            if (record.getPatientId()
                    .equalsIgnoreCase(patientId)) {

                result.add(record);
            }
        }

        result.sort(
                Comparator.comparing(
                        MedicalRecord::getDate
                ).reversed()
        );

        return result;
    }

    // Get records created by a doctor
    public List<MedicalRecord> getDoctorMedicalRecords(
            String doctorId)
            throws DoctorNotFoundException {

        // Verify doctor
        doctorService.findDoctorById(doctorId);

        List<MedicalRecord> result =
                new ArrayList<>();

        for (MedicalRecord record :
                medicalRecords) {

            if (record.getDoctorId()
                    .equalsIgnoreCase(doctorId)) {

                result.add(record);
            }
        }

        result.sort(
                Comparator.comparing(
                        MedicalRecord::getDate
                ).reversed()
        );

        return result;
    }

    // Delete medical record
    public void deleteMedicalRecord(
            String recordId)
            throws MedicalRecordException {

        MedicalRecord record =
                findMedicalRecordById(recordId);

        medicalRecords.remove(record);

        saveMedicalRecords();
    }

    public int getMedicalRecordCount() {
        return medicalRecords.size();
    }

    private void saveMedicalRecords() {

        List<String> lines =
                new ArrayList<>();

        for (MedicalRecord record :
                medicalRecords) {

            String line =
                    record.getRecordId() + "|" +
                    record.getPatientId() + "|" +
                    record.getDoctorId() + "|" +
                    record.getDate() + "|" +
                    record.getDiagnosis() + "|" +
                    record.getPrescription() + "|" +
                    record.getNotes();

            lines.add(line);
        }

        try {

            FileManager.writeToFile(
                    RECORD_FILE,
                    lines
            );

        } catch (IOException e) {

            System.out.println(
                    "Warning: Unable to save medical records."
            );
        }
    }

    private void loadMedicalRecords() {

        try {

            List<String> lines =
                    FileManager.readFromFile(
                            RECORD_FILE
                    );

            for (String line : lines) {

                String[] data =
                        line.split("\\|", -1);

                if (data.length != 7) {
                    continue;
                }

                MedicalRecord record =
                        new MedicalRecord(
                                data[0],
                                data[1],
                                data[2],
                                LocalDate.parse(data[3]),
                                data[4],
                                data[5],
                                data[6]
                        );

                medicalRecords.add(record);
            }

        } catch (Exception e) {

            System.out.println(
                    "Warning: Unable to load medical records."
            );
        }
    }
}