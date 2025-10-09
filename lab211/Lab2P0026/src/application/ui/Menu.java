package application.ui;

import java.util.Arrays;

public class Menu {

    public static void print(String str) {
        Arrays.stream(str.split("\\|")).forEach(
                System.out::println
        );
        System.out.print("Select: ");
    }

    public static void printRequireNotFound() {
        System.out.println("Require not found. Please try again!");
    }
}
