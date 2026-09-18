# 🏥 MediQueue – Problem Statement

## 📌 Project Title

**MediQueue – Hospital Appointment & Queue Management System**

---

# 🎯 1. Problem Statement

Hospitals and clinics manage several types of information on a daily basis, including patient details, doctor information, appointments, waiting queues, and medical records.

When these activities are handled manually or through disconnected records, several difficulties can occur, such as:

- ❌ Difficulty in maintaining patient information
- ❌ Difficulty in finding doctor details
- ❌ Appointment scheduling conflicts
- ❌ Inefficient management of waiting queues
- ❌ Difficulty retrieving previous medical records
- ❌ Duplicate or inconsistent information
- ❌ Increased administrative effort

Therefore, there is a need for a simple and organized software system that can manage these activities through a centralized workflow.

**MediQueue** aims to address this problem by providing a Java-based Hospital Appointment & Queue Management System that allows users to manage patients, doctors, appointments, queues, medical records, and basic reports through a command-line interface.

The system also provides input validation, exception handling, appointment conflict checking, priority-based queue management, and local data persistence.

---

# 🔭 2. Scope of the Project

The scope of MediQueue includes the following major areas:

### 👤 Patient Management

The system allows users to:

- Register patients
- View patient information
- Search patients
- Update patient information
- Delete patient records
- Validate patient details

### 👨‍⚕️ Doctor Management

The system allows users to:

- Add doctors
- View doctor information
- Search doctors
- Search doctors by specialization
- Update doctor information
- Manage doctor availability
- Delete doctor records

### 📅 Appointment Management

The system allows users to:

- Book appointments
- View appointments
- Search appointments
- Cancel appointments
- Reschedule appointments
- Check doctor availability
- Prevent appointment conflicts
- Validate patient and doctor IDs

### 🏥 Queue Management

The system provides:

- Patient queue registration
- Emergency priority handling
- Current queue viewing
- Next-patient processing
- Queue history
- Queue statistics

### 📋 Medical Record Management

The system supports:

- Creating medical records
- Viewing medical records
- Searching records
- Viewing patient medical history
- Viewing doctor-wise records
- Deleting records

### 📊 Reports & Statistics

The system provides basic:

- Appointment reports
- Doctor-wise information
- Patient statistics
- Queue statistics

### 💾 Data Persistence

The system stores application data locally using text files so that records can be loaded again after restarting the application.

---

# 👥 3. Target Users

The primary target users of MediQueue are:

### 🧑‍💼 Hospital Receptionists

Receptionists can use the system to:

- Register patients
- Manage appointments
- Check doctor availability
- Manage patient queues

### 🏢 Clinic / Hospital Administrators

Administrators can use the system to:

- Manage doctors
- Manage patient information
- Monitor appointments
- View reports and statistics

### 👨‍⚕️ Doctors

Doctors can be represented in the system through:

- Doctor profiles
- Specializations
- Availability
- Appointment information
- Medical records

### 🎓 Academic Users

The project can also be used by students and instructors to demonstrate how Java programming concepts can be applied to a real-world management problem.

---

# ✨ 4. High-Level Features

## 👤 Patient Management

- Patient registration
- Patient search
- Patient viewing
- Patient update
- Patient deletion
- Input validation
- Unique patient ID generation

---

## 👨‍⚕️ Doctor Management

- Doctor registration
- Doctor search
- Doctor viewing
- Specialization search
- Doctor update
- Availability management
- Doctor deletion
- Unique doctor ID generation

---

## 📅 Appointment Management

- Appointment booking
- Appointment search
- Appointment viewing
- Appointment cancellation
- Appointment rescheduling
- Appointment status management
- Doctor availability checking
- Appointment conflict prevention

---

## 🏥 Queue Management

- Patient queue registration
- Emergency priority
- Regular queue handling
- Next-patient processing
- Queue status
- Queue statistics

---

## 📋 Medical Records

- Record creation
- Record search
- Medical history
- Doctor-wise records
- Record viewing
- Record deletion

---

## 📊 Reports

- Daily appointment information
- Doctor-wise appointments
- Patient statistics
- Queue statistics

---

# 📥 5. Expected Inputs

The system accepts inputs such as:

### Patient Information

```text
Name
Phone
Email
Age
Gender
Blood Group
```

### Doctor Information

```text
Name
Phone
Email
Specialization
Qualification
Availability
```

### Appointment Information

```text
Patient ID
Doctor ID
Appointment Date
Appointment Time
```

### Queue Information

```text
Patient ID
Appointment ID
Priority
```

### Medical Record Information

```text
Patient ID
Doctor ID
Date
Diagnosis
Prescription
Notes
```

---

# 📤 6. Expected Outputs

The system produces:

- ✅ Successful operation messages
- 🆔 Generated IDs
- 👤 Patient information
- 👨‍⚕️ Doctor information
- 📅 Appointment information
- 🏥 Queue information
- 📋 Medical history
- 📊 Reports and statistics
- ⚠️ Validation messages
- ❌ Error messages for invalid operations

---

# 🔄 7. High-Level Workflow
```text
                    START
                      │
                      ▼
                Main Menu
                      │
                      ▼
              Select Module
                      │
        ┌─────────────┼──────────────┐
        ▼             ▼              ▼
     Patient       Doctor       Appointment
    Management    Management     Management
        │             │              │
        └─────────────┼──────────────┘
                      ▼
               Input Validation
                      │
                ┌─────┴─────┐
                │           │
             Invalid       Valid
                │           │
                ▼           ▼
          Display Error   Service Logic
                            │
                            ▼
                       Update Data
                            │
                            ▼
                     Save Data
                            │
                            ▼
                     Display Result
                            │
                            ▼
                       Main Menu
                            │
                            ▼
                           EXIT
```

---

# 🛠️ 8. Technical Scope

MediQueue is implemented using:

- ☕ Java
- 🧱 Object-Oriented Programming
- 📦 Java Collections
- 🚨 PriorityQueue
- 🗺️ HashMap
- 📋 ArrayList
- ⚠️ Exception Handling
- 📁 File I/O
- 📅 Java Date/Time API
- 🔐 Input Validation
- 🔀 Git & GitHub

The project is organized into separate packages for:

```text
model/
service/
exception/
util/
```

This structure helps maintain modularity and separation of responsibilities.

---

# ⚙️ 9. Non-Functional Scope

MediQueue is designed with the following non-functional considerations:

### 🧑‍💻 Usability

The application provides a menu-driven command-line interface with clear prompts and messages.

### ⚡ Performance

In-memory Java collections are used for efficient access to records during application execution.

### 🛡️ Reliability

Input validation, exception handling, and persistent storage are used to reduce invalid operations and data loss during normal use.

### 🔧 Maintainability

The application is divided into meaningful classes and packages, making the code easier to understand and modify.

### 💾 Resource Efficiency

The project uses lightweight local text files rather than requiring a database server.

### 📈 Scalability

The service-oriented structure allows future integration with a database or graphical/web interface.

### ⚠️ Error Handling

Invalid IDs, invalid input, appointment conflicts, and other invalid operations are handled through validation and custom exceptions.

---

# 🚧 10. Project Limitations

The current version has the following limitations:

- The interface is command-line based.
- Local text files are used instead of a database.
- Production-level authentication is not implemented.
- Advanced security mechanisms are not implemented.
- The system is intended for academic purposes rather than real hospital deployment.
- The application does not provide clinical diagnosis or treatment recommendations.

---

# 🎓 11. Academic Purpose

MediQueue is developed to demonstrate the practical application of Java programming concepts in a real-world problem domain.

The project demonstrates:

- Object-Oriented Programming
- Modular programming
- Java Collections
- Exception handling
- File handling
- Input validation
- Data structures
- Testing
- Version control using Git/GitHub

**💡 MediQueue combines Java programming concepts with a practical hospital-management workflow to create a modular and maintainable academic project.**