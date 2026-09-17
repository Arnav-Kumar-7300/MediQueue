package service;

import exception.DoctorNotFoundException;
import exception.InvalidAppointmentException;
import exception.PatientNotFoundException;
import model.Appointment;
import model.AppointmentStatus;
import model.Doctor;
import model.Patient;
import util.IDGenerator;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import util.FileManager;

import java.io.IOException;

public class AppointmentService {

    private static final String APPOINTMENT_FILE =
            "data/appointments.txt";

    private final List<Appointment> appointments;
    private final PatientService patientService;
    private final DoctorService doctorService;

    public AppointmentService(
            PatientService patientService,
            DoctorService doctorService) {

        appointments = new ArrayList<>();

        this.patientService = patientService;
        this.doctorService = doctorService;

        loadAppointments();
    }

    // Book a new appointment
    public Appointment bookAppointment(
            String patientId,
            String doctorId,
            LocalDate date,
            LocalTime time)
            throws PatientNotFoundException,
            DoctorNotFoundException,
            InvalidAppointmentException {

        // Check whether patient exists
        patientService.findPatientById(patientId);

        // Check whether doctor exists
        Doctor doctor = doctorService.findDoctorById(doctorId);

        // Check doctor availability
        if (!doctor.isAvailable()) {
            throw new InvalidAppointmentException(
                    "Doctor is currently unavailable."
            );
        }

        // Appointment date cannot be in the past
        if (date.isBefore(LocalDate.now())) {
            throw new InvalidAppointmentException(
                    "Appointment date cannot be in the past."
            );
        }

        // Check for appointment conflicts
        for (Appointment appointment : appointments) {

            if (appointment.getStatus() == AppointmentStatus.CANCELLED) {
                continue;
            }

            // Same doctor, same date and same time
            if (appointment.getDoctorId().equalsIgnoreCase(doctorId)
                    && appointment.getDate().equals(date)
                    && appointment.getTime().equals(time)) {

                throw new InvalidAppointmentException(
                        "Doctor already has an appointment at this date and time."
                );
            }

            // Same patient, same date and same time
            if (appointment.getPatientId().equalsIgnoreCase(patientId)
                    && appointment.getDate().equals(date)
                    && appointment.getTime().equals(time)) {

                throw new InvalidAppointmentException(
                        "Patient already has an appointment at this date and time."
                );
            }
        }

        String appointmentId =
                IDGenerator.generateAppointmentId();

        Appointment appointment = new Appointment(
                appointmentId,
                patientId,
                doctorId,
                date,
                time
        );

        appointments.add(appointment);

        saveAppointments();

        return appointment;
    }

    // Return all appointments sorted by date and time
    public List<Appointment> getAllAppointments() {

        List<Appointment> sortedAppointments =
                new ArrayList<>(appointments);

        sortedAppointments.sort(
                Comparator.comparing(Appointment::getDate)
                        .thenComparing(Appointment::getTime)
        );

        return sortedAppointments;
    }

    // Find appointment by ID
    public Appointment findAppointmentById(
            String appointmentId)
            throws InvalidAppointmentException {

        for (Appointment appointment : appointments) {

            if (appointment.getAppointmentId()
                    .equalsIgnoreCase(appointmentId)) {

                return appointment;
            }
        }

        throw new InvalidAppointmentException(
                "Appointment with ID "
                        + appointmentId
                        + " was not found."
        );
    }

    // Get appointments of a patient
    public List<Appointment> getPatientAppointments(
            String patientId)
            throws PatientNotFoundException {

        // Verify patient exists
        patientService.findPatientById(patientId);

        List<Appointment> result = new ArrayList<>();

        for (Appointment appointment : appointments) {

            if (appointment.getPatientId()
                    .equalsIgnoreCase(patientId)) {

                result.add(appointment);
            }
        }

        result.sort(
                Comparator.comparing(Appointment::getDate)
                        .thenComparing(Appointment::getTime)
        );

        return result;
    }

    // Get appointments of a doctor
    public List<Appointment> getDoctorAppointments(
            String doctorId)
            throws DoctorNotFoundException {

        // Verify doctor exists
        doctorService.findDoctorById(doctorId);

        List<Appointment> result = new ArrayList<>();

        for (Appointment appointment : appointments) {

            if (appointment.getDoctorId()
                    .equalsIgnoreCase(doctorId)) {

                result.add(appointment);
            }
        }

        result.sort(
                Comparator.comparing(Appointment::getDate)
                        .thenComparing(Appointment::getTime)
        );

        return result;
    }

    // Cancel appointment
    public void cancelAppointment(
            String appointmentId)
            throws InvalidAppointmentException {

        Appointment appointment =
                findAppointmentById(appointmentId);

        if (appointment.getStatus()
                == AppointmentStatus.CANCELLED) {

            throw new InvalidAppointmentException(
                    "Appointment is already cancelled."
            );
        }

        appointment.setStatus(
                AppointmentStatus.CANCELLED
        );

        saveAppointments();
    }

    // Reschedule appointment
    public void rescheduleAppointment(
            String appointmentId,
            LocalDate newDate,
            LocalTime newTime)
            throws InvalidAppointmentException {

        Appointment appointment =
                findAppointmentById(appointmentId);

        if (appointment.getStatus()
                == AppointmentStatus.CANCELLED) {

            throw new InvalidAppointmentException(
                    "Cancelled appointment cannot be rescheduled."
            );
        }

        if (newDate.isBefore(LocalDate.now())) {

            throw new InvalidAppointmentException(
                    "New appointment date cannot be in the past."
            );
        }

        // Check for conflicts
        for (Appointment existing : appointments) {

            if (existing == appointment) {
                continue;
            }

            if (existing.getStatus()
                    == AppointmentStatus.CANCELLED) {
                continue;
            }

            // Same doctor conflict
            if (existing.getDoctorId()
                    .equalsIgnoreCase(
                            appointment.getDoctorId())
                    && existing.getDate().equals(newDate)
                    && existing.getTime().equals(newTime)) {

                throw new InvalidAppointmentException(
                        "Doctor already has another appointment at this date and time."
                );
            }

            // Same patient conflict
            if (existing.getPatientId()
                    .equalsIgnoreCase(
                            appointment.getPatientId())
                    && existing.getDate().equals(newDate)
                    && existing.getTime().equals(newTime)) {

                throw new InvalidAppointmentException(
                        "Patient already has another appointment at this date and time."
                );
            }
        }

        appointment.setDate(newDate);
        appointment.setTime(newTime);
        appointment.setStatus(
                AppointmentStatus.RESCHEDULED
        );

        saveAppointments();
    }

    // Mark appointment as completed
    public void completeAppointment(
            String appointmentId)
            throws InvalidAppointmentException {

        Appointment appointment =
                findAppointmentById(appointmentId);

        if (appointment.getStatus()
                == AppointmentStatus.CANCELLED) {

            throw new InvalidAppointmentException(
                    "Cancelled appointment cannot be completed."
            );
        }

        appointment.setStatus(
                AppointmentStatus.COMPLETED
        );

        saveAppointments();
    }

    public int getAppointmentCount() {
        return appointments.size();
    }

    public int getActiveAppointmentCount() {

        int count = 0;

        for (Appointment appointment : appointments) {

            if (appointment.getStatus()
                    != AppointmentStatus.CANCELLED) {

                count++;
            }
        }

        return count;
    }

    private void saveAppointments() {

        List<String> lines =
                new ArrayList<>();

        for (Appointment appointment :
                appointments) {

            String line =
                    appointment.getAppointmentId() + "|" +
                    appointment.getPatientId() + "|" +
                    appointment.getDoctorId() + "|" +
                    appointment.getDate() + "|" +
                    appointment.getTime() + "|" +
                    appointment.getStatus();

            lines.add(line);
        }

        try {

            FileManager.writeToFile(
                    APPOINTMENT_FILE,
                    lines
            );

        } catch (IOException e) {

            System.out.println(
                    "Warning: Unable to save appointment data."
            );
        }
    }

    private void loadAppointments() {

        try {

            List<String> lines =
                    FileManager.readFromFile(
                            APPOINTMENT_FILE
                    );

            for (String line : lines) {

                String[] data =
                        line.split("\\|", -1);

                if (data.length != 6) {
                    continue;
                }

                Appointment appointment =
                        new Appointment(
                                data[0],
                                data[1],
                                data[2],
                                java.time.LocalDate.parse(
                                        data[3]
                                ),
                                java.time.LocalTime.parse(
                                        data[4]
                                )
                        );

                appointment.setStatus(
                        model.AppointmentStatus.valueOf(
                                data[5]
                        )
                );

                appointments.add(appointment);
            }

        } catch (Exception e) {

            System.out.println(
                    "Warning: Unable to load appointment data."
            );
        }
    }
}