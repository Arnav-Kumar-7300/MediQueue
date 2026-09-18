# 📐 MediQueue – System Architecture

## 1. Introduction

MediQueue follows a simple modular and layered architecture to organize the different components of the Hospital Appointment & Queue Management System.

The architecture separates user interaction, business logic, data models, exception handling, utility functions, and data storage.

---

## 2. Architecture Overview

The major layers of the system are:

1. Presentation Layer
2. Service Layer
3. Model Layer
4. Utility Layer
5. Data Storage Layer
6. Exception Handling Layer

### Architecture Diagram

```text
                    ┌──────────────────────┐
                    │       User           │
                    │   Hospital Staff     │
                    └──────────┬───────────┘
                               │
                               ▼
                    ┌──────────────────────┐
                    │    Presentation      │
                    │      Main.java       │
                    │      CLI Menu        │
                    └──────────┬───────────┘
                               │
                               ▼
                    ┌──────────────────────┐
                    │    Service Layer     │
                    │                      │
                    │ PatientService       │
                    │ DoctorService        │
                    │ AppointmentService   │
                    │ QueueService         │
                    │ MedicalRecordService │
                    └──────────┬───────────┘
                               │
                               ▼
                    ┌──────────────────────┐
                    │     Model Layer      │
                    │                      │
                    │ Patient              │
                    │ Doctor               │
                    │ Appointment          │
                    │ MedicalRecord        │
                    │ QueueEntry           │
                    └──────────┬───────────┘
                               │
                               ▼
                    ┌──────────────────────┐
                    │    Utility Layer     │
                    │                      │
                    │ FileManager          │
                    │ InputValidator       │
                    │ IDGenerator          │
                    └──────────┬───────────┘
                               │
                               ▼
                    ┌──────────────────────┐
                    │    Data Storage      │
                    │                      │
                    │ patients.txt         │
                    │ doctors.txt          │
                    │ appointments.txt     │
                    │ queue.txt            │
                    │ medical_records.txt  │
                    └──────────────────────┘
```

---

## 3. Presentation Layer

The presentation layer is responsible for interaction with the user.

### Main.java

Main.java acts as the entry point of the application.

It provides:

- Main menu
- Module selection
- User input
- Display of results
- Navigation between modules
- Error messages

The application uses a menu-driven Command Line Interface (CLI).

---

## 4. Service Layer

The service layer contains the main business logic of MediQueue.

### PatientService

Responsible for:

- Registering patients
- Searching patients
- Updating patient information
- Deleting patients
- Viewing patient information

### DoctorService

Responsible for:

- Adding doctors
- Searching doctors
- Updating doctor information
- Deleting doctors
- Managing doctor availability
- Searching doctors by specialization

### AppointmentService

Responsible for:

- Booking appointments
- Viewing appointments
- Searching appointments
- Cancelling appointments
- Rescheduling appointments
- Completing appointments
- Checking appointment conflicts
- Validating doctor availability

### QueueService

Responsible for:

- Adding patients to the queue
- Managing regular and emergency patients
- Calling the next patient
- Viewing the current queue
- Maintaining queue statistics

### MedicalRecordService

Responsible for:

- Adding medical records
- Searching records
- Viewing patient medical history
- Viewing doctor-related records
- Deleting medical records

---

## 5. Model Layer

The model layer represents the main entities of the hospital system.

### User

User is an abstract base class containing common information such as:

- ID
- Name
- Phone
- Email

### Patient

Extends User and contains patient-specific information such as:

- Age
- Gender
- Blood group

### Doctor

Extends User and contains:

- Specialization
- Qualification
- Availability

### Appointment

Represents a patient appointment with:

- Appointment ID
- Patient ID
- Doctor ID
- Date
- Time
- Appointment status

### MedicalRecord

Represents a patient's medical information including:

- Record ID
- Patient ID
- Doctor ID
- Date
- Diagnosis
- Prescription
- Notes

### QueueEntry

Represents a patient waiting in the hospital queue.

---

## 6. Utility Layer

The utility layer provides reusable functions used by different parts of the application.

### FileManager

Responsible for:

- Creating the data directory
- Reading files
- Writing files
- Appending data
- Clearing files

### InputValidator

Responsible for validating:

- Names
- Phone numbers
- Email addresses
- Age
- Gender
- Blood group

### IDGenerator

Generates unique IDs for:

- Patients
- Doctors
- Appointments
- Medical records
- Queue entries

The generator checks existing stored data so that IDs can continue correctly after restarting the application.

---

## 7. Data Storage Layer

MediQueue uses text files for persistent data storage.

```text
data/
├── patients.txt
├── doctors.txt
├── appointments.txt
├── queue.txt
└── medical_records.txt
```

Data is stored using a pipe-separated format.

Example:


```text
P1001|Rahul Sharma|9876543210|rahul@gmail.com|20|Male|O+
```

When the application starts, existing records are loaded from the files.

When records are added, updated, or deleted, the corresponding files are updated.

---

## 8. Exception Handling Layer

The application uses custom exceptions to handle important error conditions.

Examples include:

```text
PatientNotFoundException
DoctorNotFoundException
InvalidAppointmentException
QueueException
MedicalRecordException
```

These exceptions allow the application to display meaningful error messages instead of terminating unexpectedly.

---

## 9. Application Workflow

The overall workflow of MediQueue is:

```text
Start Application
        ↓
Load Existing Data
        ↓
Display Main Menu
        ↓
Select Required Module
        ↓
Enter User Input
        ↓
Validate Input
        ↓
Execute Business Logic
        ↓
Update Data
        ↓
Save Data to File
        ↓
Display Result
        ↓
Return to Main Menu
        ↓
Exit Application
```

---

## 10. Appointment Booking Workflow

```text
Enter Patient ID
        ↓
Check Patient
        ↓
Enter Doctor ID
        ↓
Check Doctor
        ↓
Check Doctor Availability
        ↓
Enter Appointment Date
        ↓
Enter Appointment Time
        ↓
Check Appointment Conflicts
        ↓
Create Appointment
        ↓
Generate Appointment ID
        ↓
Save Appointment
        ↓
Display Appointment Details
```

---

## 11. Queue Management Workflow

```text
Patient Arrives
      ↓
Enter Patient Information
      ↓
Select Queue Priority
      ↓
Add Patient to Queue
      ↓
PriorityQueue Organizes Entries
      ↓
Call Next Patient
      ↓
Update Queue Status
```

Emergency patients are given higher priority than regular patients.

---

## 12. Design Approach

The project uses modular design so that each major responsibility is handled by a separate class or service.

This provides:

- Better organization
- Easier maintenance
- Code reusability
- Easier debugging
- Separation of responsibilities
- Easier future expansion

---

## 13. Technologies Used

- Java
- Java Collections Framework
- Java File I/O
- Java Date & Time API
- Object-Oriented Programming
- Exception Handling
- Visual Studio Code
- Git
- GitHub

---

## 14. Conclusion

The layered architecture of MediQueue separates user interaction from business logic and data management.

This modular structure makes the application easier to understand, test, maintain, and extend with additional hospital-management features in the future.


