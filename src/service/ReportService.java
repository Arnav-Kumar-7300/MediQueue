package service;

import model.Appointment;
import model.AppointmentStatus;
import model.Doctor;
import model.MedicalRecord;
import model.Patient;
import model.QueueEntry;
import model.QueueEntryStatus;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportService {

    private final PatientService patientService;
    private final DoctorService doctorService;
    private final AppointmentService appointmentService;
    private final QueueService queueService;
    private final MedicalRecordService medicalRecordService;

    public ReportService(
            PatientService patientService,
            DoctorService doctorService,
            AppointmentService appointmentService,
            QueueService queueService,
            MedicalRecordService medicalRecordService) {

        this.patientService = patientService;
        this.doctorService = doctorService;
        this.appointmentService = appointmentService;
        this.queueService = queueService;
        this.medicalRecordService = medicalRecordService;
    }

    // ---------------- PATIENT REPORT ----------------

    public int getTotalPatients() {

        return patientService
                .getPatientCount();
    }

    // ---------------- DOCTOR REPORT ----------------

    public int getTotalDoctors() {

        return doctorService
                .getDoctorCount();
    }

    public int getAvailableDoctors() {

        return doctorService
                .getAvailableDoctorCount();
    }

    public int getUnavailableDoctors() {

        return getTotalDoctors()
                - getAvailableDoctors();
    }

    // ---------------- APPOINTMENT REPORT ----------------

    public int getTotalAppointments() {

        return appointmentService
                .getAppointmentCount();
    }

    public int getActiveAppointments() {

        return appointmentService
                .getActiveAppointmentCount();
    }

    public int getCompletedAppointments() {

        int count = 0;

        List<Appointment> appointments =
                appointmentService
                        .getAllAppointments();

        for (Appointment appointment :
                appointments) {

            if (appointment.getStatus()
                    == AppointmentStatus.COMPLETED) {

                count++;
            }
        }

        return count;
    }

    public int getCancelledAppointments() {

        int count = 0;

        List<Appointment> appointments =
                appointmentService
                        .getAllAppointments();

        for (Appointment appointment :
                appointments) {

            if (appointment.getStatus()
                    == AppointmentStatus.CANCELLED) {

                count++;
            }
        }

        return count;
    }

    public int getScheduledAppointments() {

        int count = 0;

        List<Appointment> appointments =
                appointmentService
                        .getAllAppointments();

        for (Appointment appointment :
                appointments) {

            if (appointment.getStatus()
                    == AppointmentStatus.SCHEDULED) {

                count++;
            }
        }

        return count;
    }

    public int getRescheduledAppointments() {

        int count = 0;

        List<Appointment> appointments =
                appointmentService
                        .getAllAppointments();

        for (Appointment appointment :
                appointments) {

            if (appointment.getStatus()
                    == AppointmentStatus.RESCHEDULED) {

                count++;
            }
        }

        return count;
    }

    // ---------------- TODAY'S APPOINTMENTS ----------------

    public List<Appointment> getTodaysAppointments() {

        LocalDate today =
                LocalDate.now();

        List<Appointment> result =
                new java.util.ArrayList<>();

        for (Appointment appointment :
                appointmentService
                        .getAllAppointments()) {

            if (appointment.getDate()
                    .equals(today)) {

                result.add(appointment);
            }
        }

        return result;
    }

    // ---------------- DOCTOR-WISE REPORT ----------------

    public Map<String, Integer>
    getDoctorWiseAppointmentCount() {

        Map<String, Integer> report =
                new HashMap<>();

        List<Appointment> appointments =
                appointmentService
                        .getAllAppointments();

        for (Appointment appointment :
                appointments) {

            if (appointment.getStatus()
                    == AppointmentStatus.CANCELLED) {

                continue;
            }

            String doctorId =
                    appointment.getDoctorId();

            report.put(
                    doctorId,
                    report.getOrDefault(
                            doctorId,
                            0
                    ) + 1
            );
        }

        return report;
    }

    // ---------------- PATIENT-WISE REPORT ----------------

    public Map<String, Integer>
    getPatientWiseAppointmentCount() {

        Map<String, Integer> report =
                new HashMap<>();

        List<Appointment> appointments =
                appointmentService
                        .getAllAppointments();

        for (Appointment appointment :
                appointments) {

            if (appointment.getStatus()
                    == AppointmentStatus.CANCELLED) {

                continue;
            }

            String patientId =
                    appointment.getPatientId();

            report.put(
                    patientId,
                    report.getOrDefault(
                            patientId,
                            0
                    ) + 1
            );
        }

        return report;
    }

    // ---------------- QUEUE REPORT ----------------

    public int getWaitingPatients() {

        return queueService
                .getWaitingCount();
    }

    public int getServedPatients() {

        return queueService
                .getServedCount();
    }

    public int getRemovedPatients() {

        return queueService
                .getRemovedCount();
    }

    public int getTotalQueueEntries() {

        return queueService
                .getTotalEntries();
    }

    // ---------------- MEDICAL RECORD REPORT ----------------

    public int getTotalMedicalRecords() {

        return medicalRecordService
                .getMedicalRecordCount();
    }

    // ---------------- DASHBOARD ----------------

    public void displayDashboard() {

        System.out.println();
        System.out.println(
                "========================================"
        );
        System.out.println(
                "          MEDIQUEUE DASHBOARD"
        );
        System.out.println(
                "========================================"
        );

        System.out.println();

        System.out.println(
                "PATIENTS"
        );

        System.out.println(
                "Total Patients       : "
                        + getTotalPatients()
        );

        System.out.println();

        System.out.println(
                "DOCTORS"
        );

        System.out.println(
                "Total Doctors        : "
                        + getTotalDoctors()
        );

        System.out.println(
                "Available Doctors    : "
                        + getAvailableDoctors()
        );

        System.out.println(
                "Unavailable Doctors  : "
                        + getUnavailableDoctors()
        );

        System.out.println();

        System.out.println(
                "APPOINTMENTS"
        );

        System.out.println(
                "Total Appointments   : "
                        + getTotalAppointments()
        );

        System.out.println(
                "Scheduled            : "
                        + getScheduledAppointments()
        );

        System.out.println(
                "Completed            : "
                        + getCompletedAppointments()
        );

        System.out.println(
                "Cancelled            : "
                        + getCancelledAppointments()
        );

        System.out.println(
                "Rescheduled          : "
                        + getRescheduledAppointments()
        );

        System.out.println();

        System.out.println(
                "QUEUE"
        );

        System.out.println(
                "Currently Waiting    : "
                        + getWaitingPatients()
        );

        System.out.println(
                "Patients Served      : "
                        + getServedPatients()
        );

        System.out.println(
                "Patients Removed     : "
                        + getRemovedPatients()
        );

        System.out.println();

        System.out.println(
                "MEDICAL RECORDS"
        );

        System.out.println(
                "Total Records        : "
                        + getTotalMedicalRecords()
        );

        System.out.println();

        System.out.println(
                "========================================"
        );
    }
}