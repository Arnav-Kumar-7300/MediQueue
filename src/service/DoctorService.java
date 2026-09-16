package service;

import exception.DoctorNotFoundException;
import model.Doctor;
import util.IDGenerator;

import java.util.ArrayList;
import java.util.List;

public class DoctorService {

    private final List<Doctor> doctors;

    public DoctorService() {
        doctors = new ArrayList<>();
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
    }

    // Delete doctor
    public void deleteDoctor(String doctorId)
            throws DoctorNotFoundException {

        Doctor doctor = findDoctorById(doctorId);

        doctors.remove(doctor);
    }

    // Change doctor availability
    public void setDoctorAvailability(
            String doctorId,
            boolean available)
            throws DoctorNotFoundException {

        Doctor doctor = findDoctorById(doctorId);

        doctor.setAvailable(available);
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
}