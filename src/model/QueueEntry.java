package model;

import java.time.LocalDateTime;

public class QueueEntry {

    private String queueId;
    private String patientId;
    private String appointmentId;
    private int priority;
    private LocalDateTime arrivalTime;
    private QueueEntryStatus status;

    public QueueEntry(
            String queueId,
            String patientId,
            String appointmentId,
            int priority) {

        this.queueId = queueId;
        this.patientId = patientId;
        this.appointmentId = appointmentId;
        this.priority = priority;
        this.arrivalTime = LocalDateTime.now();
        this.status = QueueEntryStatus.WAITING;
    }

    public String getQueueId() {
        return queueId;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public int getPriority() {
        return priority;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public QueueEntryStatus getStatus() {
        return status;
    }

    public void setStatus(QueueEntryStatus status) {
        this.status = status;
    }

    public void displayDetails() {

        System.out.println("Queue ID       : " + queueId);
        System.out.println("Patient ID     : " + patientId);

        if (appointmentId == null
                || appointmentId.isEmpty()) {

            System.out.println("Appointment ID : Walk-in");
        } else {

            System.out.println(
                    "Appointment ID : " + appointmentId
            );
        }

        System.out.println("Priority       : "
                + getPriorityLabel());

        System.out.println("Arrival Time   : "
                + arrivalTime);

        System.out.println("Status         : "
                + status);
    }

    public String getPriorityLabel() {

        switch (priority) {

            case 1:
                return "EMERGENCY";

            case 2:
                return "URGENT";

            default:
                return "REGULAR";
        }
    }
}
