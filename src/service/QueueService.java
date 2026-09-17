package service;

import exception.QueueException;
import model.Appointment;
import model.AppointmentStatus;
import model.QueueEntry;
import model.QueueEntryStatus;
import util.IDGenerator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import util.FileManager;

import java.io.IOException;
import java.time.LocalDateTime;

public class QueueService {

    private static final String QUEUE_FILE =
            "data/queue.txt";

    private final Queue<QueueEntry> patientQueue;
    private final Map<String, QueueEntry> queueHistory;
    private final PatientService patientService;
    private final AppointmentService appointmentService;

    public QueueService(
            PatientService patientService,
            AppointmentService appointmentService) {

        this.patientQueue = new PriorityQueue<>(
                Comparator
                        .comparingInt(
                                QueueEntry::getPriority)
                        .thenComparing(
                                QueueEntry::getArrivalTime)
        );

        this.queueHistory = new HashMap<>();

        this.patientService = patientService;
        this.appointmentService = appointmentService;

        loadQueue();
    }

    // Add patient to queue
    public QueueEntry addToQueue(
            String patientId,
            String appointmentId,
            int priority)
            throws QueueException {

        // Check patient
        try {

            patientService.findPatientById(patientId);

        } catch (Exception e) {

            throw new QueueException(
                    "Patient with ID "
                            + patientId
                            + " was not found."
            );
        }

        // Validate priority
        if (priority < 1 || priority > 3) {

            throw new QueueException(
                    "Invalid priority. Use 1 for Emergency, "
                            + "2 for Urgent, or 3 for Regular."
            );
        }

        // Check appointment if provided
        if (appointmentId != null
                && !appointmentId.isEmpty()) {

            try {

                Appointment appointment =
                        appointmentService.findAppointmentById(
                                appointmentId
                        );

                if (!appointment.getPatientId()
                        .equalsIgnoreCase(patientId)) {

                    throw new QueueException(
                            "Appointment does not belong to this patient."
                    );
                }

                if (appointment.getStatus()
                        == AppointmentStatus.CANCELLED) {

                    throw new QueueException(
                            "Cancelled appointment cannot be added to queue."
                    );
                }

            } catch (QueueException e) {

                throw e;

            } catch (Exception e) {

                throw new QueueException(
                        "Invalid appointment ID."
                );
            }
        }

        // Prevent duplicate waiting entries
        for (QueueEntry entry : patientQueue) {

            if (entry.getPatientId()
                    .equalsIgnoreCase(patientId)) {

                throw new QueueException(
                        "Patient is already in the queue."
                );
            }
        }

        String queueId =
                IDGenerator.generateQueueId();

        QueueEntry entry = new QueueEntry(
                queueId,
                patientId,
                appointmentId,
                priority
        );

        patientQueue.add(entry);

        queueHistory.put(queueId, entry);

        saveQueue();

        return entry;
    }

    // View current queue
    public List<QueueEntry> getCurrentQueue() {

        List<QueueEntry> result =
                new ArrayList<>(patientQueue);

        result.sort(
                Comparator
                        .comparingInt(
                                QueueEntry::getPriority)
                        .thenComparing(
                                QueueEntry::getArrivalTime)
        );

        return result;
    }

    // Find queue entry
    public QueueEntry findQueueEntry(
            String queueId)
            throws QueueException {

        QueueEntry entry =
                queueHistory.get(queueId);

        if (entry == null) {

            throw new QueueException(
                    "Queue entry with ID "
                            + queueId
                            + " was not found."
            );
        }

        return entry;
    }

    // Call next patient
    public QueueEntry callNextPatient()
            throws QueueException {

        if (patientQueue.isEmpty()) {

            throw new QueueException(
                    "No patients are currently waiting."
            );
        }

        QueueEntry entry =
                patientQueue.poll();

        entry.setStatus(
                QueueEntryStatus.SERVED
        );

        saveQueue();

        return entry;
    }

    // Remove patient from queue
    public void removeFromQueue(
            String queueId)
            throws QueueException {

        QueueEntry entry =
                findQueueEntry(queueId);

        if (entry.getStatus()
                != QueueEntryStatus.WAITING) {

            throw new QueueException(
                    "Patient is not currently waiting in the queue."
            );
        }

        boolean removed =
                patientQueue.remove(entry);

        if (!removed) {

            throw new QueueException(
                    "Unable to remove patient from queue."
            );
        }

        entry.setStatus(
                QueueEntryStatus.REMOVED
        );

        saveQueue();
    }

    public int getWaitingCount() {

        return patientQueue.size();
    }

    public int getServedCount() {

        int count = 0;

        for (QueueEntry entry :
                queueHistory.values()) {

            if (entry.getStatus()
                    == QueueEntryStatus.SERVED) {

                count++;
            }
        }

        return count;
    }

    public int getRemovedCount() {

        int count = 0;

        for (QueueEntry entry :
                queueHistory.values()) {

            if (entry.getStatus()
                    == QueueEntryStatus.REMOVED) {

                count++;
            }
        }

        return count;
    }

    public int getTotalEntries() {

        return queueHistory.size();
    }

    private void saveQueue() {

        List<String> lines =
                new ArrayList<>();

        for (QueueEntry entry :
                queueHistory.values()) {

            String appointmentId =
                    entry.getAppointmentId();

            if (appointmentId == null) {
                appointmentId = "";
            }

            String line =
                    entry.getQueueId() + "|" +
                    entry.getPatientId() + "|" +
                    appointmentId + "|" +
                    entry.getPriority() + "|" +
                    entry.getArrivalTime() + "|" +
                    entry.getStatus();

            lines.add(line);
        }

        try {

            FileManager.writeToFile(
                    QUEUE_FILE,
                    lines
            );

        } catch (IOException e) {

            System.out.println(
                    "Warning: Unable to save queue data."
            );
        }
    }

    private void loadQueue() {

        try {

            List<String> lines =
                    FileManager.readFromFile(
                            QUEUE_FILE
                    );

            for (String line : lines) {

                String[] data =
                        line.split("\\|", -1);

                if (data.length != 6) {
                    continue;
                }

                String appointmentId =
                        data[2].isEmpty()
                                ? null
                                : data[2];

                QueueEntry entry =
                        new QueueEntry(
                                data[0],
                                data[1],
                                appointmentId,
                                Integer.parseInt(data[3]),
                                LocalDateTime.parse(
                                        data[4]
                                ),
                                QueueEntryStatus.valueOf(
                                        data[5]
                                )
                        );

                queueHistory.put(
                        entry.getQueueId(),
                        entry
                );

                if (entry.getStatus()
                        == QueueEntryStatus.WAITING) {

                    patientQueue.add(entry);
                }
            }

        } catch (Exception e) {

            System.out.println(
                    "Warning: Unable to load queue data."
            );
        }
    }
}