package util;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IDGenerator {

    private static int patientCounter = 1000;
    private static int doctorCounter = 2000;
    private static int appointmentCounter = 3000;
    private static int recordCounter = 4000;
    private static int queueCounter = 5000;

    private static boolean initialized = false;

    // Initialize counters from existing files
    public static void initialize() {

        if (initialized) {
            return;
        }

        patientCounter =
                getHighestId(
                        "data/patients.txt",
                        "P",
                        1000
                );

        doctorCounter =
                getHighestId(
                        "data/doctors.txt",
                        "D",
                        2000
                );

        appointmentCounter =
                getHighestId(
                        "data/appointments.txt",
                        "A",
                        3000
                );

        recordCounter =
                getHighestId(
                        "data/medical_records.txt",
                        "R",
                        4000
                );

        queueCounter =
                getHighestId(
                        "data/queue.txt",
                        "Q",
                        5000
                );

        initialized = true;
    }

    private static int getHighestId(
            String fileName,
            String prefix,
            int defaultValue) {

        int highest = defaultValue;

        File file =
                new File(fileName);

        if (!file.exists()) {
            return highest;
        }

        try {

            java.util.Scanner scanner =
                    new java.util.Scanner(file);

            while (scanner.hasNextLine()) {

                String line =
                        scanner.nextLine();

                if (line.startsWith(prefix)) {

                    String[] parts =
                            line.split("\\|");

                    String id = parts[0];

                    Pattern pattern =
                            Pattern.compile(
                                    prefix + "(\\d+)"
                            );

                    Matcher matcher =
                            pattern.matcher(id);

                    if (matcher.matches()) {

                        int number =
                                Integer.parseInt(
                                        matcher.group(1)
                                );

                        if (number > highest) {
                            highest = number;
                        }
                    }
                }
            }

            scanner.close();

        } catch (Exception e) {

            System.out.println(
                    "Warning: Unable to initialize ID counter for "
                            + prefix
            );
        }

        return highest;
    }

    public static String generatePatientId() {
        initialize();
        return "P" + (++patientCounter);
    }

    public static String generateDoctorId() {
        initialize();
        return "D" + (++doctorCounter);
    }

    public static String generateAppointmentId() {
        initialize();
        return "A" + (++appointmentCounter);
    }

    public static String generateRecordId() {
        initialize();
        return "R" + (++recordCounter);
    }

    public static String generateQueueId() {
        initialize();
        return "Q" + (++queueCounter);
    }
}