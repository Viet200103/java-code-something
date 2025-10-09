package business.utilities;

public final class DataValidation {

    public static void requirePositiveNumber(int value, String msg) throws IllegalArgumentException {
        if (value < 0) {
            throw new IllegalArgumentException(msg);
        }
    }

    public static void requirePositiveNumber(String number, String msg) throws IllegalArgumentException {
        if (!number.matches("\\d{1,10}")) {
            throw new IllegalArgumentException(msg);
        }
    }

    public static void requireNotNullEmpty(String text, String msg) throws  IllegalArgumentException {
        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException(msg);
        }
    }

}
