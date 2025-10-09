package application.utilities;

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

    public String getString() {
        return scanner.nextLine();
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

    public static void displayDateExample() {
        printlnMessage(">> Format of Date: dd-mm-yyyy, Ex: 01-01-1970");
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
