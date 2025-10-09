package application.ui;

import java.util.Arrays;

public final class Menu {

    public static void print(String str) {
        Arrays.stream(str.split("\\|")).forEach(
                System.out::println
        );
        System.out.print("Select: ");
    }

    public static void printRequireNotFound() {
        System.out.println("Require not found. Please try again!");
    }

    public static void callProductManagement() {
        ProductManagement management = new ProductManagement();
        management.displayMenu();
    }

    public static void callWarehouseManagement() {
        WarehouseManagement management = new WarehouseManagement();
        management.displayMenu();
    }

    public static void callReportManagement() {
        ReportManagement management = new ReportManagement();
        management.displayMenu();
    }
}
