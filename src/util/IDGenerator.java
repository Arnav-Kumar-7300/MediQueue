package util;

public class IDGenerator {

    private static int patientCounter = 1000;
    private static int doctorCounter = 2000;
    private static int appointmentCounter = 3000;
    private static int recordCounter = 4000;
    private static int queueCounter = 5000;

    public static String generatePatientId() {
        return "P" + (++patientCounter);
    }

    public static String generateDoctorId() {
        return "D" + (++doctorCounter);
    }

    public static String generateAppointmentId() {
        return "A" + (++appointmentCounter);
    }

    public static String generateRecordId() {
        return "R" + (++recordCounter);
    }

    public static String generateQueueId() {
        return "Q" + (++queueCounter);
    }
}