package application;

import application.ui.Menu;
import application.utilities.DataIOHelper;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        DataIOHelper inputHelper = DataIOHelper.getInstance();

        try {
            int choice;

            do {
                System.out.println("******Store Program******");
                Menu.print(
                        "1. Products management" + "|" +
                        "2. Warehouse management" + "|" +
                        "3. Report" + "|" +
                        "4. Exit"
                );
                choice = inputHelper.getIntegerNumber();
                System.out.println("--------------------------------");

                switch (choice) {
                    case 1 -> Menu.callProductManagement();
                    case 2 -> Menu.callWarehouseManagement();
                    case 3 -> Menu.callReportManagement();
                    case 4 -> {
                        System.out.println("***Store Program is stopped***");
                        System.exit(0);
                    }
                    default -> Menu.printRequireNotFound();
                }

            } while (true);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}