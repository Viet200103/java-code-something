package application.utilities;

public final class DataValidation {

    public static void requirePositiveNumber(int value, String msg) throws IllegalArgumentException {
        if (value < 0) {
            throw new IllegalArgumentException(msg);
        }
    }

    public static void requirePositiveNumber(String number, String msg) throws IllegalArgumentException {
        if (number.length() < 10 || !number.matches("\\d{1,10}")) {
            throw new IllegalArgumentException(msg);
        }
    }

    public static void requireNotNullEmpty(String text, String msg) throws  IllegalArgumentException {
        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException(msg);
        }
    }

    public static void validateFlightNumber(String fNumber) throws Exception {
        if (fNumber.length() < 5 || !fNumber.matches("F\\d{4}")) {
            throw new IllegalArgumentException("Invalid flight number format");
        }
    }

    public static void validatePhoneNumber(String phone) {
        if (!phone.matches("\\d{1,11}")) {
            throw new IllegalArgumentException("Invalid phone number");
        }
    }
}
