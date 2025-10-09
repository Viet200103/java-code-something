package application.utilities;

import java.util.Date;
import java.util.Scanner;

public class DataIOHelper {

    private final Scanner scanner = new Scanner(System.in);

    private DataIOHelper() {

    }

    public int getIntegerNumber() throws Exception {
        int number;
        String s;
        s = scanner.nextLine();
        if (!s.matches("\\d{1,10}")) {
            throw new Exception("Data invalid.");
        } else {
            number = Integer.parseInt(s);
        }
        return number;
    }

    public int getIntegerNumberWithMessage(String displayMessage) throws Exception {
        System.out.print(displayMessage);
        return getIntegerNumber();
    }

    public String getStringWithMessage(String displayMessage) {
        System.out.print(displayMessage);
        return getString();
    }

    public String getStringNotEmpty(String displayMessage, String errMsg) {
        String outputStr;
        do {
            outputStr = getStringWithMessage(displayMessage);
            if (!outputStr.isBlank()) {
                break;
            } else {
                System.out.println(">> " + errMsg);
                displayTryAgainMessage();
            }
        } while (true);
        return outputStr;
    }

    public String getPhoneNumber(String displayMessage) {
        String phone;
        do {
            try {
                DataIOHelper.printMessage(displayMessage);
                phone = getString();
                DataValidation.validatePhoneNumber(phone);
                break;
            } catch (Exception e) {
                DataIOHelper.printlnNotification(e.getMessage());
                DataIOHelper.displayTryAgainMessage();
            }
        } while (true);

        return phone;
    }

    public String getDateTime(String msg, boolean isEmpty) {
        String date;
        do {
            try {
                DataIOHelper.printMessage(msg);
                date = getString();

                if (isEmpty && date.isBlank()) {
                    return date;
                }

                DateUtils.checkFormatDate(date);
                break;
            } catch (Exception e) {
                DataIOHelper.printlnNotification(e.getMessage());
                DataIOHelper.printlnNotification("Format of Date: dd-MM-yyyy");
                DataIOHelper.displayTryAgainMessage();
            }
        } while (true);

        return date;
    }

    public String getDateTime(String msg) {
        return getDateTime(msg, false);
    }


    public String getTime(String msg) {
        String time;
        do {
            try {
                DataIOHelper.printMessage(msg);
                time = getString();
                DateUtils.checkFormatTime(time);
                break;
            } catch (Exception e) {
                DataIOHelper.printlnNotification(e.getMessage());
                DataIOHelper.printlnNotification("Format of time: HH:mm");
                DataIOHelper.displayTryAgainMessage();
            }
        } while (true);
        return time;
    }

    public String getString() {
        return scanner.nextLine();
    }

    public String getFlightNumber(String msg, boolean isEmpty) {
        String fNumber;
        do {
            try {
                fNumber = getStringWithMessage(msg);
                if (isEmpty && fNumber.isBlank()) {
                    return null;
                }
                DataValidation.validateFlightNumber(fNumber);
                break;
            } catch (Exception e) {
                DataIOHelper.printlnNotification(e.getMessage());
                DataIOHelper.displayTryAgainMessage();
            }
        } while (true);

        return fNumber;
    }

    public static void printlnMessage(String msg) {
        System.out.println(msg);
    }

    public static void printlnNotification(String msg) {
        System.out.println(">> " + msg);
    }

    public static void printMessage(String msg) {
        System.out.print(msg);
    }

    public static void displayTryAgainMessage() {
        System.out.println(">> Please try again!.");
    }

    private static DataIOHelper INSTANCE = null;

    public static DataIOHelper getInstance() {
        if (INSTANCE == null) {
            synchronized (DataIOHelper.class) {
                INSTANCE = new DataIOHelper();
            }
        }
        return INSTANCE;
    }
}
