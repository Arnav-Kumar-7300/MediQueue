package util;

public class InputValidator {

    public static boolean isValidName(String name) {

        return name != null
                && !name.trim().isEmpty()
                && name.matches("[a-zA-Z ]+");
    }

    public static boolean isValidPhone(String phone) {

        return phone != null
                && phone.matches("[0-9]{10}");
    }

    public static boolean isValidEmail(String email) {

        return email != null
                && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    public static boolean isValidAge(int age) {

        return age > 0 && age <= 120;
    }

    public static boolean isValidGender(String gender) {

        return gender.equalsIgnoreCase("Male")
                || gender.equalsIgnoreCase("Female")
                || gender.equalsIgnoreCase("Other");
    }

    public static boolean isValidBloodGroup(String bloodGroup) {

        String[] validGroups = {
                "A+", "A-",
                "B+", "B-",
                "AB+", "AB-",
                "O+", "O-"
        };

        for (String group : validGroups) {

            if (group.equalsIgnoreCase(bloodGroup)) {
                return true;
            }
        }

        return false;
    }
}