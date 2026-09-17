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

public class QueueService {

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
}