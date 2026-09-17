package service;

import exception.DoctorNotFoundException;
import model.Doctor;
import util.IDGenerator;

import java.util.ArrayList;
import java.util.List;

import util.FileManager;

import java.io.IOException;

public class DoctorService {

    private static final String DOCTOR_FILE =
            "data/doctors.txt";

    private final List<Doctor> doctors;

    public DoctorService() {

        doctors = new ArrayList<>();

        loadDoctors();
    }

    // Add a new doctor
    public Doctor addDoctor(
            String name,
            String phone,
            String email,
            String specialization,
            String qualification) {

        String doctorId = IDGenerator.generateDoctorId();

        Doctor doctor = new Doctor(
                doctorId,
                name,
                phone,
                email,
                specialization,
                qualification
        );

        doctors.add(doctor);

        saveDoctors();

        return doctor;
    }

    // Get all doctors
    public List<Doctor> getAllDoctors() {

        return doctors;
    }

    // Find doctor by ID
    public Doctor findDoctorById(String doctorId)
            throws DoctorNotFoundException {

        for (Doctor doctor : doctors) {

            if (doctor.getId().equalsIgnoreCase(doctorId)) {
                return doctor;
            }
        }

        throw new DoctorNotFoundException(
                "Doctor with ID " + doctorId + " was not found."
        );
    }

    // Search doctors by specialization
    public List<Doctor> findDoctorsBySpecialization(
            String specialization) {

        List<Doctor> result = new ArrayList<>();

        for (Doctor doctor : doctors) {

            if (doctor.getSpecialization()
                    .equalsIgnoreCase(specialization)) {

                result.add(doctor);
            }
        }

        return result;
    }

    // Update doctor information
    public void updateDoctor(
            String doctorId,
            String name,
            String phone,
            String email,
            String specialization,
            String qualification)
            throws DoctorNotFoundException {

        Doctor doctor = findDoctorById(doctorId);

        doctor.setName(name);
        doctor.setPhone(phone);
        doctor.setEmail(email);
        doctor.setSpecialization(specialization);
        doctor.setQualification(qualification);

        saveDoctors();
    }

    // Delete doctor
    public void deleteDoctor(String doctorId)
            throws DoctorNotFoundException {

        Doctor doctor = findDoctorById(doctorId);

        doctors.remove(doctor);

        saveDoctors();
    }

    // Change doctor availability
    public void setDoctorAvailability(
            String doctorId,
            boolean available)
            throws DoctorNotFoundException {

        Doctor doctor = findDoctorById(doctorId);

        doctor.setAvailable(available);

        saveDoctors();
    }

    // Count total doctors
    public int getDoctorCount() {

        return doctors.size();
    }

    // Count available doctors
    public int getAvailableDoctorCount() {

        int count = 0;

        for (Doctor doctor : doctors) {

            if (doctor.isAvailable()) {
                count++;
            }
        }

        return count;
    }

    private void saveDoctors() {

        List<String> lines =
                new ArrayList<>();

        for (Doctor doctor : doctors) {

            String line =
                    doctor.getId() + "|" +
                    doctor.getName() + "|" +
                    doctor.getPhone() + "|" +
                    doctor.getEmail() + "|" +
                    doctor.getSpecialization() + "|" +
                    doctor.getQualification() + "|" +
                    doctor.isAvailable();

            lines.add(line);
        }

        try {

            FileManager.writeToFile(
                    DOCTOR_FILE,
                    lines
            );

        } catch (IOException e) {

            System.out.println(
                    "Warning: Unable to save doctor data."
            );
        }
    }

    private void loadDoctors() {

        try {

            List<String> lines =
                    FileManager.readFromFile(
                            DOCTOR_FILE
                    );

            for (String line : lines) {

                String[] data =
                        line.split("\\|", -1);

                if (data.length != 7) {
                    continue;
                }

                Doctor doctor =
                        new Doctor(
                                data[0],
                                data[1],
                                data[2],
                                data[3],
                                data[4],
                                data[5]
                        );

                doctor.setAvailable(
                        Boolean.parseBoolean(data[6])
                );

                doctors.add(doctor);
            }

        } catch (Exception e) {

            System.out.println(
                    "Warning: Unable to load doctor data."
            );
        }
    }
}