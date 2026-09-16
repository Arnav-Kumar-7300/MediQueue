package model;

public class Patient extends User {

    private int age;
    private String gender;
    private String bloodGroup;

    public Patient(
            String id,
            String name,
            String phone,
            String email,
            int age,
            String gender,
            String bloodGroup) {

        super(id, name, phone, email);

        this.age = age;
        this.gender = gender;
        this.bloodGroup = bloodGroup;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    @Override
    public void displayDetails() {

        System.out.println("Patient ID   : " + getId());
        System.out.println("Name         : " + getName());
        System.out.println("Phone        : " + getPhone());
        System.out.println("Email        : " + getEmail());
        System.out.println("Age          : " + age);
        System.out.println("Gender       : " + gender);
        System.out.println("Blood Group  : " + bloodGroup);
    }
}
