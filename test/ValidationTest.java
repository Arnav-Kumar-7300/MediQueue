import util.InputValidator;

public class ValidationTest {

    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("       MediQueue Testing");
        System.out.println("=================================\n");

        testNameValidation();
        testPhoneValidation();
        testEmailValidation();
        testAgeValidation();
        testGenderValidation();
        testBloodGroupValidation();

        System.out.println("\n=================================");
        System.out.println("        Testing Summary");
        System.out.println("=================================");

        System.out.println("Tests Passed : " + passed);
        System.out.println("Tests Failed : " + failed);

        if (failed == 0) {
            System.out.println("\nAll tests passed successfully!");
        } else {
            System.out.println("\nSome tests failed.");
        }
    }

    // Test name validation
    private static void testNameValidation() {

        check(
                InputValidator.isValidName("Rahul Sharma"),
                "Valid patient name"
        );

        check(
                !InputValidator.isValidName("12345"),
                "Invalid patient name"
        );
    }

    // Test phone validation
    private static void testPhoneValidation() {

        check(
                InputValidator.isValidPhone("9876543210"),
                "Valid phone number"
        );

        check(
                !InputValidator.isValidPhone("12345"),
                "Invalid phone number"
        );
    }

    // Test email validation
    private static void testEmailValidation() {

        check(
                InputValidator.isValidEmail("rahul@gmail.com"),
                "Valid email"
        );

        check(
                !InputValidator.isValidEmail("rahul@gmail"),
                "Invalid email"
        );
    }

    // Test age validation
    private static void testAgeValidation() {

        check(
                InputValidator.isValidAge(25),
                "Valid age"
        );

        check(
                !InputValidator.isValidAge(150),
                "Invalid age"
        );
    }

    // Test gender validation
    private static void testGenderValidation() {

        check(
                InputValidator.isValidGender("Male"),
                "Valid gender"
        );

        check(
                !InputValidator.isValidGender("Unknown"),
                "Invalid gender"
        );
    }

    // Test blood group validation
    private static void testBloodGroupValidation() {

        check(
                InputValidator.isValidBloodGroup("O+"),
                "Valid blood group"
        );

        check(
                !InputValidator.isValidBloodGroup("X+"),
                "Invalid blood group"
        );
    }

    // Common test method
    private static void check(boolean condition, String testName) {

        if (condition) {
            System.out.println("[PASS] " + testName);
            passed++;
        } else {
            System.out.println("[FAIL] " + testName);
            failed++;
        }
    }
}