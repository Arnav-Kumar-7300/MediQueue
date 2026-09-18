# 🏥 MediQueue – Hospital Appointment & Queue Management System

> A Java-based command-line system for managing patients, doctors, appointments, hospital queues, medical records, and basic reports.

---

## 📌 Project Overview

**MediQueue** is a Java-based Hospital Appointment & Queue Management System designed to simplify and organize common hospital management activities.

The system provides a centralized workflow for:

- 👤 Patient management
- 👨‍⚕️ Doctor management
- 📅 Appointment scheduling
- 🏥 Queue management
- 📋 Medical record management
- 📊 Reports and statistics

MediQueue is developed as a **modular Java CLI application** using Object-Oriented Programming, Java Collections, Exception Handling, File I/O, Input Validation, and Git/GitHub.

The system uses local text files for data persistence, allowing important records to remain available even after restarting the application.

> ⚠️ **Academic Disclaimer:** MediQueue is developed for educational purposes. It is not a production hospital information system and does not provide medical diagnosis or clinical decision-making.

---

## 🎯 Problem Being Addressed

Hospitals and clinics manage large amounts of information related to patients, doctors, appointments, waiting queues, and medical records.

Managing these activities manually or through disconnected records can result in:

- ❌ Difficulty in retrieving patient information
- ❌ Appointment scheduling conflicts
- ❌ Difficulty managing waiting queues
- ❌ Duplicate or inconsistent records
- ❌ Difficulty maintaining medical history
- ❌ Increased administrative effort

MediQueue provides a centralized software solution to organize these activities through a simple and user-friendly command-line interface.

---

## ✨ Features

### 👤 1. Patient Management

The Patient Management module allows the system to:

- ➕ Register new patients
- 👀 View all patients
- 🔍 Search patients by ID
- ✏️ Update patient information
- 🗑️ Delete patient records
- ✅ Validate patient information
- 🆔 Generate unique patient IDs

Patient details include:

- Name
- Phone number
- Email
- Age
- Gender
- Blood group

---

### 👨‍⚕️ 2. Doctor Management

The Doctor Management module provides:

- ➕ Add new doctors
- 👀 View all doctors
- 🔍 Search doctors by ID
- 🔎 Search doctors by specialization
- ✏️ Update doctor information
- 🟢 Change doctor availability
- 🗑️ Delete doctor records
- 🆔 Generate unique doctor IDs

Doctor details include:

- Name
- Phone number
- Email
- Specialization
- Qualification
- Availability

---

### 📅 3. Appointment Management

The Appointment Management module allows users to:

- 📌 Book appointments
- 🔍 Search appointments
- 👀 View appointments
- ❌ Cancel appointments
- 🔄 Reschedule appointments
- 📊 Track appointment status
- ✅ Validate patient and doctor IDs
- 🕐 Check appointment date and time
- 🚫 Prevent conflicting appointment slots
- 🩺 Check doctor availability

Appointment statuses include:

```text
SCHEDULED
COMPLETED
CANCELLED
RESCHEDULED
```
---

### 🏥 4. Queue Management

MediQueue includes a dedicated hospital waiting queue system.

Features include:

- ➕ Add patients to the queue
- 🚨 Support emergency priority
- 👀 View the current queue
- 📢 Call the next patient
- 🗑️ Remove queue entries
- 📊 View queue statistics
- 📝 Maintain queue history

The system uses Java's **PriorityQueue** to process patients according to their priority.

---

### 📋 5. Medical Record Management

The Medical Record module allows users to:

- ➕ Add medical records
- 👀 View all medical records
- 🔍 Search medical records
- 📜 View patient medical history
- 👨‍⚕️ View doctor-wise records
- 🗑️ Delete medical records

Records contain information such as:

- Patient ID
- Doctor ID
- Date
- Diagnosis
- Prescription
- Notes

---

### 📊 6. Reports & Statistics

The system provides basic reports such as:

- 📅 Daily appointment information
- 👨‍⚕️ Doctor-wise appointment information
- 👤 Patient statistics
- 🏥 Queue statistics

These reports provide a quick overview of the information managed by the system.

---

## 🗂️ Project Structure

```text
MediQueue/
│
├── README.md
├── statement.md
├── .gitignore
│
├── data/
│   ├── patients.txt
│   ├── doctors.txt
│   ├── appointments.txt
│   ├── queue.txt
│   └── medical_records.txt
│
├── src/
│   ├── Main.java
│   │
│   ├── model/
│   │   ├── User.java
│   │   ├── Patient.java
│   │   ├── Doctor.java
│   │   ├── Appointment.java
│   │   ├── MedicalRecord.java
│   │   └── AppointmentStatus.java
│   │
│   ├── service/
│   │   ├── PatientService.java
│   │   ├── DoctorService.java
│   │   ├── AppointmentService.java
│   │   ├── QueueService.java
│   │   └── MedicalRecordService.java
│   │
│   ├── exception/
│   │   ├── PatientNotFoundException.java
│   │   ├── DoctorNotFoundException.java
│   │   └── InvalidAppointmentException.java
│   │
│   └── util/
│       ├── FileManager.java
│       ├── InputValidator.java
│       └── IDGenerator.java
│
├── test/
│   └── ValidationTest.java
│
└── docs/
    └── screenshots/
```

---

## 🛠️ Technologies & Tools Used

```text
Technology/Tool          | Purpose                             
```
```text
☕ Java                  | Core application development        
```
```text
🧱 OOP                   | Object-oriented system design      
```
```text
📦 ArrayList             | Managing application records       
```
```text
🚨 PriorityQueue         | Emergency/priority queue management
```
```text
🗺️ HashMap               | Queue/history data management      
```
```text
📁 Java File I/O         | Persistent local storage           
```
```text
⚠️ Exception Handling    | Handling invalid operations        
```
```text
📅 LocalDate / LocalTime | Appointment date and time          
```
```text
🔐 Regular Expressions   | Input validation                   
```
```text
💻 VS Code               | Development environment            
```
```text
🌐 GitHub                | Repository and project submission  
```
```text
🔀 Git                   | Version control                    
```

---

## 🧠 Java Concepts Demonstrated

MediQueue demonstrates several important Java programming concepts.

### 🔹 Object-Oriented Programming

- Classes and Objects
- Encapsulation
- Inheritance
- Abstraction
- Method Overriding
- Constructors
- Getters and Setters

### 🔹 Collections

The project uses:

```text
ArrayList
PriorityQueue
HashMap
```

### 🔹 Exception Handling

Custom exceptions are used for domain-specific errors, including:

```text
PatientNotFoundException
DoctorNotFoundException
InvalidAppointmentException
Queue-related exceptions
MedicalRecord-related exceptions
```

### 🔹 File Handling

The FileManager class provides reusable operations for:

- Reading files
- Writing files
- Appending data
- Creating the data directory
- Clearing files

### 🔹 Input Validation

The InputValidator class validates:

- Names
- Phone numbers
- Emails
- Age
- Gender
- Blood group

---

## 🏗️ System Architecture

MediQueue follows a layered modular architecture:

```text
                👤 USER
                  │
                  ▼
          ┌─────────────────┐
          │   CLI / Main    │
          │  User Interface │
          └────────┬────────┘
                   │
                   ▼
          ┌─────────────────┐
          │ Service Layer   │
          │                 │
          │ PatientService  │
          │ DoctorService   │
          │ AppointmentSvc  │
          │ QueueService    │
          │ MedicalRecordSvc│
          └────────┬────────┘
                   │
                   ▼
          ┌─────────────────┐
          │   Model Layer   │
          │                 │
          │ Patient         │
          │ Doctor          │
          │ Appointment     │
          │ MedicalRecord   │
          └────────┬────────┘
                   │
                   ▼
          ┌─────────────────┐
          │ Utility Layer   │
          │                 │
          │ FileManager     │
          │ InputValidator  │
          │ IDGenerator     │
          └────────┬────────┘
                   │
                   ▼
          ┌─────────────────┐
          │ Local Storage   │
          │   data/*.txt    │
          └─────────────────┘
```

---

## 🔄 Application Workflow

```text
START
  ↓
Main Menu
  ↓
Select Required Module
  ↓
Enter Required Information
  ↓
Input Validation
  ↓
Execute Service Logic
  ↓
Update Data
  ↓
Save Data to File
  ↓
Display Result
  ↓
Return to Main Menu
  ↓
EXIT
```

---

## 💾 Data Persistence

MediQueue stores data locally in text files.

```text
data/
│
├── patients.txt
├── doctors.txt
├── appointments.txt
├── queue.txt
└── medical_records.txt
```

Example patient record:

```text
P1001|Rahul Sharma|9876543210|rahul@gmail.com|20|Male|O+
```

Example doctor record:

```text
D2001|Dr. Amit Kumar|9876543211|amit@gmail.com|Cardiology|MBBS, MD|true
```

The application loads stored data when it starts and saves changes during relevant operations.

---

## ⚙️ Installation & Setup

### Prerequisites

Install the following:

- ☕ Java Development Kit (JDK)
- 💻 Visual Studio Code
- 🔀 Git

Check Java installation:

```text
java -version
```
```text
javac -version
```

---

## 🚀 Running the Project

### Step 1 – Clone the Repository

```text
git clone <YOUR_GITHUB_REPOSITORY_URL>
```

Move into the project folder:

```text
cd MediQueue
```

### Step 2 – Compile the Project

Run the following command from the project root:

```text
javac -d out src/model/*.java src/util/*.java src/exception/*.java src/service/*.java src/Main.java
```

### Step 3 – Run the Application

```text
java -cp out Main
```

--- 

## 🧪 Testing

MediQueue includes a simple validation test class:

```text
test/ValidationTest.java
```

Compile the test:

```text
javac -cp out -d out test/ValidationTest.java
```

Run the test:

```text
java -cp out ValidationTest
```

### 🔍 Testing Areas

The project should be tested for:

- ✅ Valid patient registration
- ❌ Invalid patient information
- ✅ Valid doctor registration
- ❌ Invalid doctor IDs
- ✅ Appointment booking
- ❌ Appointment conflicts
- ❌ Invalid appointment dates
- 🚨 Emergency queue priority
- 📋 Medical record validation
- 💾 Data persistence after restart
- 🆔 ID generation after restart

---

## 🖥️ Example Application Output

```text
========================================
              MediQueue
 Hospital Appointment & Queue System
========================================

1. Patient Management
2. Doctor Management
3. Appointment Management
4. Queue Management
5. Medical Records
6. Reports
7. Exit

Enter your choice:
```

Example:

```text
Enter Patient Name: Rahul Sharma
Enter Phone: 9876543210
Enter Email: rahul@gmail.com
Enter Age: 20
Enter Gender: Male
Enter Blood Group: O+

Patient registered successfully!
Patient ID: P1001
```

---

## ⚠️ Error Handling

MediQueue validates user input and provides meaningful error messages.

For example:

```text
Enter Patient ID: P9999

Error: Patient with ID P9999 was not found.
```

The system validates IDs at the appropriate point in the workflow so that invalid references are detected before unrelated information is entered.

---

## 💡 Design Decisions

### Why Java?

Java was selected because the project is intended to demonstrate programming concepts such as OOP, collections, exception handling, file handling, and modular programming.

### Why CLI?

A command-line interface keeps the application focused on Java programming and allows the complete project to run directly from the terminal.

### Why Text Files?

Text files provide simple persistent storage without requiring a separate database server or external dependency.

### Why PriorityQueue?

A hospital queue may need to process emergency patients before regular patients. PriorityQueue provides a suitable data structure for implementing this behavior.

---

## 🔮 Future Enhancements

Possible future improvements include:

- 🗄️ MySQL/PostgreSQL database integration
- 🔐 User authentication
- 👥 Role-based access for administrators, doctors, and receptionists
- 🌐 Web-based interface
- 🖥️ Graphical user interface
- 🧪 JUnit automated testing
- 🔔 Appointment reminders
- 📊 Advanced analytics and dashboards
- 📝 Audit logging
- 💾 Backup and recovery
- 🔒 Improved data protection

---

## 📚 Academic Note

MediQueue is developed as an academic project to demonstrate Java programming and software development concepts.

It is **not intended to replace professional hospital information systems** and should not be used for real clinical diagnosis or treatment decisions.

---

## 👨‍💻 Project

MediQueue – Hospital Appointment & Queue Management System

Built using ❤️ Java, OOP, Collections, Exception Handling, File I/O and Git.


