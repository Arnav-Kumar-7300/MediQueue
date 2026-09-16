package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Appointment {

    private String appointmentId;
    private String patientId;
    private String doctorId;
    private LocalDate date;
    private LocalTime time;
    private AppointmentStatus status;

    public Appointment(
            String appointmentId,
            String patientId,
            String doctorId,
            LocalDate date,
            LocalTime time) {

        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.date = date;
        this.time = time;
        this.status = AppointmentStatus.SCHEDULED;
    }

    public String getAppointmentId() {
        return appointmentId;
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

    public LocalTime getTime() {
        return time;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public void displayDetails() {

        System.out.println("Appointment ID : " + appointmentId);
        System.out.println("Patient ID     : " + patientId);
        System.out.println("Doctor ID      : " + doctorId);
        System.out.println("Date           : " + date);
        System.out.println("Time           : " + time);
        System.out.println("Status         : " + status);
    }
}