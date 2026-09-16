package model;

import java.time.LocalDate;

public class MedicalRecord {

    private String recordId;
    private String patientId;
    private String doctorId;
    private LocalDate date;
    private String diagnosis;
    private String prescription;
    private String notes;

    public MedicalRecord(
            String recordId,
            String patientId,
            String doctorId,
            LocalDate date,
            String diagnosis,
            String prescription,
            String notes) {

        this.recordId = recordId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.date = date;
        this.diagnosis = diagnosis;
        this.prescription = prescription;
        this.notes = notes;
    }

    public String getRecordId() {
        return recordId;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public String getPrescription() {
        return prescription;
    }

    public String getNotes() {
        return notes;
    }

    public void displayDetails() {

        System.out.println("Record ID     : " + recordId);
        System.out.println("Patient ID    : " + patientId);
        System.out.println("Doctor ID     : " + doctorId);
        System.out.println("Date          : " + date);
        System.out.println("Diagnosis     : " + diagnosis);
        System.out.println("Prescription  : " + prescription);
        System.out.println("Notes         : " + notes);
    }
}