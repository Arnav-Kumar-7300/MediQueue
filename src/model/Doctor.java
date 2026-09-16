package model;

public class Doctor extends User {

    private String specialization;
    private String qualification;
    private boolean available;

    public Doctor(
            String id,
            String name,
            String phone,
            String email,
            String specialization,
            String qualification) {

        super(id, name, phone, email);

        this.specialization = specialization;
        this.qualification = qualification;
        this.available = true;
    }

    public String getSpecialization() {
        return specialization;
    }

    public String getQualification() {
        return qualification;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public void displayDetails() {

        System.out.println("Doctor ID       : " + getId());
        System.out.println("Name            : " + getName());
        System.out.println("Phone           : " + getPhone());
        System.out.println("Email           : " + getEmail());
        System.out.println("Specialization  : " + specialization);
        System.out.println("Qualification   : " + qualification);
        System.out.println("Available       : " +
                (available ? "Yes" : "No"));
    }
}
